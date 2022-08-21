package com.qxy.module.personal.data.repo

import androidx.core.content.edit
import com.qxy.api.account.IAccountService
import com.qxy.lib.base.base.network.Results
import com.qxy.lib.base.base.network.processResults
import com.qxy.lib.base.base.repository.BaseRepositoryBoth
import com.qxy.lib.base.base.repository.ILocalDataSource
import com.qxy.lib.base.base.repository.IRemoteDataSource
import com.qxy.lib.base.util.ARouterUtil
import com.qxy.lib.base.util.fromJson
import com.qxy.lib.base.util.toJson
import com.qxy.lib.common.network.processApiResponse
import com.qxy.module.personal.KEY_PERSONAL_INFO
import com.qxy.module.personal.data.model.PersonalInfo
import com.qxy.module.personal.data.api.PersonalService
import com.qxy.module.personal.data.model.PersonalVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * @project DouyinQxy
 * @author Yenaly Liew
 * @time 2022/08/20 020 16:02
 */
class PersonalRepository @Inject constructor(
    remoteDataSource: PersonalRemoteDataSource,
    localDataSource: PersonalLocalDataSource
) : BaseRepositoryBoth<PersonalRemoteDataSource, PersonalLocalDataSource>(
    remoteDataSource,
    localDataSource
) {
    fun getPersonalInfo(): Flow<Results<PersonalInfo>> {
        val localFlow = flow {
            localDataSource.localPersonalInfo?.let {
                val localData = processResults { it }
                emit(localData)
            } ?: emit(Results.None)
        }
        val remoteFlow = flow {
            val accessToken = remoteDataSource.getAccessToken()
            val remoteData = processResults {
                remoteDataSource.getRemotePersonalInfo(accessToken, remoteDataSource.getOpenId())
                    .also {
                        localDataSource.localPersonalInfo = it
                    }
            }
            emit(remoteData)
        }
        return flowOf(localFlow, remoteFlow)
            .flattenMerge()
            .flowOn(Dispatchers.IO)
    }

    fun getPersonalVideo(cursor: Int, count: Int): Flow<Results<PersonalVideo>> {
        return flow {
            val result = processResults {
                remoteDataSource.getRemotePersonalVideo(
                    remoteDataSource.getOpenId(),
                    cursor, count,
                    remoteDataSource.getAccessToken()
                )
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}

class PersonalLocalDataSource @Inject constructor() : ILocalDataSource {

    private val sp = ARouterUtil.getService(IAccountService::class).sharedPref

    @set:JvmName("saveLocalPersonalInfo")
    var localPersonalInfo: PersonalInfo?
        get() {
            return sp.getString(KEY_PERSONAL_INFO, null)?.fromJson()
        }
        set(value) {
            sp.edit {
                putString(KEY_PERSONAL_INFO, value!!.toJson())
            }
        }
}

class PersonalRemoteDataSource @Inject constructor(
    private val personalService: PersonalService
) : IRemoteDataSource {
    private val service = ARouterUtil.getService(IAccountService::class)

    suspend fun getRemotePersonalInfo(accessToken: String, openID: String): PersonalInfo {
        return processApiResponse {
            personalService.getPersonalInfo(accessToken, openID)
        }
    }

    suspend fun getRemotePersonalVideo(
        openID: String,
        cursor: Int,
        count: Int,
        accessToken: String
    ) = processApiResponse {
        personalService.getPersonalVideo(openID, cursor, count, accessToken)
    }

    suspend fun getAccessToken() = service.getAccessToken()

    fun getOpenId() = service.openID
}