package com.qxy.module.personal.repo

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
import com.qxy.module.personal.model.PersonalInfo
import com.qxy.module.personal.service.PersonalService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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
    private val service = ARouterUtil.getService(IAccountService::class)

    suspend fun getPersonalInfo(): Flow<Results<PersonalInfo>> {
        val localFlow = flow {
            localDataSource.localPersonalInfo?.let {
                val localData = processResults { it }
                emit(localData)
            } ?: emit(Results.none())
        }
        val remoteFlow = flow {
            val accessToken = service.getAccessToken()
            val remoteData = processResults {
                remoteDataSource.getRemotePersonalInfo(accessToken, service.openID).also {
                    localDataSource.localPersonalInfo = it
                }
            }
            emit(remoteData)
        }
        return flowOf(localFlow, remoteFlow).flattenMerge()
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
    suspend fun getRemotePersonalInfo(accessToken: String, openID: String): PersonalInfo {
        return processApiResponse {
            personalService.getPersonalInfo(accessToken, openID)
        }
    }
}