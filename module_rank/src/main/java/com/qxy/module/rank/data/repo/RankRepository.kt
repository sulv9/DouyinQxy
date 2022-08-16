package com.qxy.module.rank.data.repo

import androidx.room.withTransaction
import com.qxy.api.account.IAccountService
import com.qxy.lib.base.base.network.*
import com.qxy.lib.base.base.repository.BaseRepositoryBoth
import com.qxy.lib.base.base.repository.ILocalDataSource
import com.qxy.lib.base.base.repository.IRemoteDataSource
import com.qxy.lib.base.ext.log
import com.qxy.lib.base.util.ARouterUtil
import com.qxy.lib.common.network.processApiResponse
import com.qxy.module.rank.data.api.RankService
import com.qxy.module.rank.data.db.RankDatabase
import com.qxy.module.rank.data.model.RankItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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
        val localFlow = flow {
            val localResult = processResults {
                // 加载本地数据
                val localData = localDataSource.getLocalRankData(type, version ?: -1)
                localData
            }
            emit(localResult.ifEmpty { Results.none() })
        }
        // 远程数据流
        val remoteFlow = flow {
            val remoteResult = processResults {
                // 加载远程数据
                val remoteData = remoteDataSource.getRemoteRankData(type, version)
                    // version为空则为其赋值为-1
                    .map { it.copy(version = version ?: -1) }
                // 保存网络请求结果
                localDataSource.saveLocalRankData(remoteData)
                // 查询数据库数据
                localDataSource.getLocalRankData(type, version ?: -1)
                    .ifEmpty { throw Errors.EmptyResultError }
            }
            emit(remoteResult)
        }
        return flowOf(localFlow, remoteFlow)
            .flattenMerge()
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
                .ifEmpty {
                    log { "getLocal Empty" }
                    throw Errors.EmptyResultError }
        }
        emit(remoteResult)
    }

    /**
     * 获取榜单版本flow
     */
    fun getRankVersionFlow(type: Int) {

    }
}

class RankRemoteDataSource @Inject constructor(
    private val rankService: RankService
) : IRemoteDataSource {
    /**
     * 获取rank网络数据
     */
    suspend fun getRemoteRankData(type: Int, version: Int? = null): List<RankItem> {
        val token = ARouterUtil.getService(IAccountService::class).getClientToken()
        return processApiResponse {
            rankService.getRankItem(token, type, version)
        }.rankList ?: throw Errors.EmptyResultError.also { log { "getRemote Empty" } }
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
                .also { if (it.isEmpty()) throw Errors.EmptyResultError }
        }
    }

    /**
     * 先清除再保存本地rank数据
     */
    suspend fun saveLocalRankData(rankItems: List<RankItem>) {
        db.withTransaction {
            db.rankDao().clearRankData()
            db.rankDao().insert(rankItems)
        }
    }

}