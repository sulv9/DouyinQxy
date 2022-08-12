package com.qxy.module.rank.data.repo

import androidx.room.withTransaction
import com.qxy.api.account.IAccountService
import com.qxy.lib.base.base.network.Errors
import com.qxy.lib.base.base.repository.BaseRepositoryBoth
import com.qxy.lib.base.base.repository.ILocalDataSource
import com.qxy.lib.base.base.repository.IRemoteDataSource
import com.qxy.lib.base.ext.AbortFlowWrapper
import com.qxy.lib.base.ext.log
import com.qxy.lib.base.util.ARouterUtil
import com.qxy.lib.common.network.processApiResponse
import com.qxy.module.rank.data.api.RankService
import com.qxy.module.rank.data.db.RankDatabase
import com.qxy.module.rank.data.model.RankItem
import com.qxy.module.rank.ui.RankViewState
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

    fun getRankFlow(type: Int, version: Int? = null): Flow<RankViewState> {
        // 本地数据流
//        val localFlow = flow {
//            log { "start local Flow" }
//            val rankLocal = localDataSource.getLocalRankData(type, version ?: -1)
//            // 本地数据先加载完，不截断流
//            emit(AbortFlowWrapper(rankLocal, false))
//        }
        // 远程数据流
        return flow {
            log { "start remote Flow" }
            val rankRemote = remoteDataSource.getRemoteRankData(type, version)
                // version为空则为其赋值为-1
                .map { it.copy(version = version ?: -1) }
            // 保存网络请求结果
            localDataSource.saveLocalRankData(rankRemote)
            // 网络数据加载完后不再接收本地数据流
//            emit(AbortFlowWrapper(rankRemote, true))
            emit(RankViewState(rankList = rankRemote))
        }
//        return flowOf(localFlow, remoteFlow)
//            .flattenMerge()
//            .transformWhile {
//                // 发送数据
//                emit(it.data)
//                log { "transformWhile" }
//                // [it.abort]为true时截断流，不再发射
//                !it.abort
//            }
//            .map { RankViewState(rankList = it).also { log { "flow map" } } }
//            .flowOn(Dispatchers.IO)
//            .catch {
//                log { "catch $it" }
//                when (it) {
//                    is Errors.ApiError -> emit(RankViewState(isApiError = true))
//                    is Errors.NetworkError -> emit(RankViewState(isNetworkError = true))
//                    is Errors.EmptyResultError -> emit(RankViewState(isEmptyError = true))
//                }
//            }
    }
}

class RankRemoteDataSource @Inject constructor(
    private val rankService: RankService
) : IRemoteDataSource {
    /**
     * 获取rank网络数据
     */
    suspend fun getRemoteRankData(type: Int, version: Int? = null): List<RankItem> {
        val token = ARouterUtil.getService(IAccountService::class).getAccessToken()
        return processApiResponse {
            log { "process Network Data - token:$token" }
            rankService.getRankItem(token, type, version)
        }.rankList.also { log { "rankList: $it" } } ?: throw Errors.EmptyResultError
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
            db.rankDao().getRankData(type, version).also { log { "db: $it" } }
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