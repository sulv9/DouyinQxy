package com.qxy.module.rank.data.repo

import androidx.paging.*
import androidx.room.withTransaction
import com.qxy.api.account.IAccountService
import com.qxy.lib.base.base.network.Errors
import com.qxy.lib.base.base.network.Results
import com.qxy.lib.base.base.network.ifEmpty
import com.qxy.lib.base.base.network.processResults
import com.qxy.lib.base.base.repository.BaseRepositoryBoth
import com.qxy.lib.base.base.repository.ILocalDataSource
import com.qxy.lib.base.base.repository.IRemoteDataSource
import com.qxy.lib.base.ext.log
import com.qxy.lib.base.ext.toast
import com.qxy.lib.base.util.ARouterUtil
import com.qxy.lib.common.network.processApiResponse
import com.qxy.module.rank.PAGE_SIZE
import com.qxy.module.rank.data.api.RankService
import com.qxy.module.rank.data.api.RankVersionService
import com.qxy.module.rank.data.db.RankDatabase
import com.qxy.module.rank.data.model.RankItem
import com.qxy.module.rank.data.model.RankVersion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RankRepository @Inject constructor(
    remoteDataSource: RankRemoteDataSource,
    localDataSource: RankLocalDataSource
) : BaseRepositoryBoth<RankRemoteDataSource, RankLocalDataSource>(
    remoteDataSource,
    localDataSource
) {
    /**
     * repo层对flow进行合流
     */
    fun getRankListFlow(type: Int, version: Int? = null): Flow<Results<List<RankItem>>> {
        // 本地数据流
        val localFlow = getRankListFromLocal(type, version)
        // 远程数据流
        val remoteFlow = getRankListFlowFromRemote(type, version)
        return flowOf(localFlow, remoteFlow)
            .flattenMerge()
            .flowOn(Dispatchers.IO)
    }

    /**
     * 只从网络请求数据，一般用于重新返回界面时调用
     */
    fun getRankListFlowFromRemote(type: Int, version: Int? = null) = flow {
        val remoteResult = processResults {
            // 加载远程数据
            val remoteData = remoteDataSource.getRemoteRankData(type, version)
                // version为空则为其赋值为-1
                .map { it.copy(version = version ?: -1) }
            // 保存网络请求结果
            localDataSource.saveLocalRankData(remoteData)
            // 查询数据库数据
            localDataSource.getLocalRankData(type, version ?: -1)
        }
        emit(remoteResult.ifEmpty { throw Errors.EmptyResultError })
    }

    /**
     * 从本地数据库获取榜单列表数据
     */
    private fun getRankListFromLocal(type: Int, version: Int? = null) = flow {
        val localResult = processResults {
            // 加载本地数据
            val localData = localDataSource.getLocalRankData(type, version ?: -1)
            localData
        }
        emit(localResult.ifEmpty { Results.None })
    }

    suspend fun getNewestRankVersionCursor(type: Int): Int {
        val nextVersion = localDataSource.fetchNextRankVersion(type)
        return nextVersion?.cursor ?: 0
    }

    /**
     * 获取榜单版本列表
     * 先加载网络数据保存，再加载本地数据
     * 返回为空表示没有更多数据
     */
    suspend fun loadRankVersion(cursor: Int, type: Int, isRefresh: Boolean) {
        if (cursor == Integer.MAX_VALUE) return
        val remoteResult = processResults {
            // 加载远程数据
            val initRankVersion = remoteDataSource.getRemoteRankVersion(cursor, PAGE_SIZE, type)
                .copy(type = type)
            initRankVersion
        }
        if (remoteResult is Results.Success) {
            // 保存网络请求结果
            if (isRefresh) {
                // 刷新
                localDataSource.clearAndInsertNewVersionData(remoteResult.value)
            } else {
                // APPEND
                localDataSource.insertNewVersionData(remoteResult.value)
            }
        } else if (remoteResult is Results.Failure) {
            toast("${remoteResult.errors.message}")
        }
    }

    fun getRankVersionList(type: Int): Flow<Results<List<RankVersion>>> {
        return localDataSource.getRankVersionStream(type)
            .map { processResults { it } }
            .flowOn(Dispatchers.IO)
    }
}

class RankRemoteDataSource @Inject constructor(
    private val rankService: RankService,
    private val versionService: RankVersionService,
) : IRemoteDataSource {
    private val mAccountService by lazy { ARouterUtil.getService(IAccountService::class) }

    /**
     * 获取rank网络数据
     */
    suspend fun getRemoteRankData(type: Int, version: Int? = null): List<RankItem> {
        val token = mAccountService.getClientToken()
        val rankData = processApiResponse {
            rankService.getRankItem(token, type, version)
        }
        if (rankData.rankList.isNullOrEmpty()) throw Errors.EmptyResultError
        return rankData.rankList.map { it.copy(activeTime = rankData.activeTime) }
    }

    suspend fun getRemoteRankVersion(cursor: Int, count: Int, type: Int): RankVersion {
        val token = mAccountService.getClientToken()
        return processApiResponse {
            versionService.getRankVersions(token, cursor, count, type)
        }.copy(type = type)
    }
}

class RankLocalDataSource @Inject constructor(
    private val db: RankDatabase
) : ILocalDataSource {

    /**
     * 获取本地rank数据
     */
    suspend fun getLocalRankData(type: Int, version: Int): List<RankItem> {
        return db.withTransaction {
            db.rankDao().getRankData(type, version)
        }
    }

    /**
     * 先清除再保存本地rank数据
     */
    suspend fun saveLocalRankData(rankList: List<RankItem>) {
        db.withTransaction {
            db.rankDao().clearRankData()
            db.rankDao().insert(rankList)
        }
    }

    /**
     * 插入前需判断[rankVersion.versionList]不能为空，为空则抛出异常
     */
    suspend fun clearAndInsertNewVersionData(rankVersion: RankVersion) {
        db.withTransaction {
            with(db.rankVersionDao()) {
                clearRankVersion()
                insert(rankVersion)
            }
        }
    }

    /**
     * 插入前需判断[rankVersion.versionList]不能为空，为空则抛出异常
     */
    suspend fun insertNewVersionData(rankVersion: RankVersion) {
        db.withTransaction {
            db.rankVersionDao().insert(rankVersion)
        }
    }

    suspend fun fetchNextRankVersion(type: Int): RankVersion? {
        return db.withTransaction {
            db.rankVersionDao().getNextRankVersion(type)
        }
    }

    fun getRankVersionStream(type: Int): Flow<List<RankVersion>> {
        return db.rankVersionDao().observeRankVersionListUntilChanged(type)
    }

}

/**
 * 使用RemoteMediator时，列表未滑到底部就会发送网络请求数据，导致数据请求量过大，因此抛弃
 */
//@OptIn(ExperimentalPagingApi::class)
//class RankVersionRemoteMediator(
//    private val remoteDataSource: RankRemoteDataSource,
//    private val localDataSource: RankLocalDataSource,
//    private val type: Int,
//) : RemoteMediator<Int, RankVersionItem>() {
//    override suspend fun initialize(): InitializeAction {
//        return InitializeAction.LAUNCH_INITIAL_REFRESH
//    }
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, RankVersionItem>
//    ): MediatorResult {
//        return try {
//            val pageSize = state.config.pageSize
//            val cursor = when (loadType) {
//                LoadType.REFRESH -> 0 // 初始时cursor为0
//                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
//                LoadType.APPEND -> {
//                    val nextRankVersion = localDataSource.fetchNextRankVersion(type)
//                    if (nextRankVersion == null || nextRankVersion.cursor == Integer.MAX_VALUE
//                        || nextRankVersion.hasMore != true) {
//                        log { "APPEND END" }
//                        return MediatorResult.Success(endOfPaginationReached = true)
//                    }
//                    log { "APPEND rankVersion $nextRankVersion" }
//                    nextRankVersion.cursor
//                }
//            }
//            val rankVersion = remoteDataSource.getRemoteRankVersion(
//                cursor, pageSize, type
//            )
//            val rankList = rankVersion.versionList ?: throw Errors.EmptyResultError
//            if (loadType == LoadType.REFRESH) {
//                localDataSource.clearAndInsertNewVersionData(rankVersion)
//            } else {
//                localDataSource.insertNewVersionData(rankVersion)
//            }
//            log { "cursor:$cursor loadType:$loadType size:${rankList.size}" }
//            MediatorResult.Success(rankList.isEmpty())
//        } catch (e: Exception) {
//            toast(e.toString())
//            MediatorResult.Error(e)
//        }
//    }
//}