package com.qxy.lib.account.repo

import android.content.Context
import androidx.core.content.edit
import com.qxy.lib.account.KEY_ACCESS_TOKEN
import com.qxy.lib.account.KEY_CLIENT_TOKEN
import com.qxy.lib.account.KEY_PERSONAL_INFO
import com.qxy.lib.account.USER_AUTH_CODE
import com.qxy.lib.account.ext.secureSharedPref
import com.qxy.lib.account.model.AccessToken
import com.qxy.lib.account.model.ClientToken
import com.qxy.lib.account.model.PersonalInfo
import com.qxy.lib.account.model.RefreshToken
import com.qxy.lib.account.network.PersonalService
import com.qxy.lib.account.network.TokenService
import com.qxy.lib.base.BuildConfig
import com.qxy.lib.base.base.network.Results
import com.qxy.lib.base.base.network.processResults
import com.qxy.lib.base.base.repository.BaseRepositoryBoth
import com.qxy.lib.base.base.repository.ILocalDataSource
import com.qxy.lib.base.base.repository.IRemoteDataSource
import com.qxy.lib.base.util.fromJson
import com.qxy.lib.base.util.toJson
import com.qxy.lib.common.network.processApiResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

/**
 * @project DouyinQxy
 * @author Yenaly Liew
 * @time 2022/08/09 009 15:45
 */
class AccountRepository @Inject constructor(
    remoteDataSource: AccountRemoteDataSource,
    localDataSource: AccountLocalDataSource
) : BaseRepositoryBoth<AccountRemoteDataSource, AccountLocalDataSource>(
    remoteDataSource,
    localDataSource
) {

    suspend fun getClientToken(): String {
        val localClientToken = localDataSource.localClientToken
        localClientToken?.let {
            if (!isTokenExpire(it.responseTime, it.expiresIn)) return it.accessToken
        }
        val remoteClientToken = remoteDataSource.getRemoteClientToken()
        localDataSource.localClientToken =
            remoteClientToken.copy(responseTime = System.currentTimeMillis())
        return remoteClientToken.accessToken
    }

    suspend fun getAccessToken(): String {
        val localAccessToken = localDataSource.localAccessToken
        localAccessToken?.let {
            if (!isTokenExpire(it.responseTime, it.expiresIn)) {
                return it.accessToken
            }
            if (isTokenExpire(it.responseTime, it.refreshExpiresIn)) {
                val newRefreshToken = remoteDataSource.getRemoteRefreshToken(it.refreshToken)
                val newAccessToken =
                    remoteDataSource.refreshRemoteAccessToken(newRefreshToken.refreshToken)
                localDataSource.localAccessToken =
                    newAccessToken.copy(responseTime = System.currentTimeMillis())
                return newAccessToken.accessToken
            }
        }
        val remoteAccessToken = remoteDataSource.getRemoteAccessToken()
        localDataSource.localAccessToken =
            remoteAccessToken.copy(responseTime = System.currentTimeMillis())
        return remoteAccessToken.accessToken
    }

    private fun isTokenExpire(savedTimestamp: Long, expireIn: Long): Boolean {
        return System.currentTimeMillis() - savedTimestamp > expireIn * 1000
    }

    suspend fun getPersonalInfo(): Flow<Results<PersonalInfo>> {
        val localFlow = flow {
            localDataSource.localPersonalInfo?.let {
                val localData = processResults { it }
                emit(localData)
            } ?: emit(Results.none())
        }
        val remoteFlow = flow {
            val accessToken = getAccessToken()
            val remoteData = processResults {
                remoteDataSource.getRemotePersonalInfo(
                    accessToken,
                    localDataSource.localAccessToken!!.openID
                )
            }
            emit(remoteData)
        }
        return flowOf(localFlow, remoteFlow).flattenMerge()
    }
}

class AccountRemoteDataSource @Inject constructor(
    @ApplicationContext context: Context,
    private val tokenService: TokenService,
    private val personalService: PersonalService
) : IRemoteDataSource {

    private val secureSharedPref = context.secureSharedPref

    suspend fun getRemoteClientToken(): ClientToken {
        return processApiResponse {
            tokenService.getClientToken(BuildConfig.DOUYIN_KEY, BuildConfig.DOUYIN_SECRET)
        }
    }

    suspend fun getRemoteAccessToken(): AccessToken {
        val authCode = secureSharedPref.getString(USER_AUTH_CODE, "") ?: ""
        return processApiResponse {
            tokenService.getAccessToken(BuildConfig.DOUYIN_SECRET, authCode, BuildConfig.DOUYIN_KEY)
        }
    }

    suspend fun getRemoteRefreshToken(refreshToken: String): RefreshToken {
        return processApiResponse {
            tokenService.getRefreshToken(BuildConfig.DOUYIN_KEY, refreshToken)
        }
    }

    suspend fun refreshRemoteAccessToken(refreshToken: String): AccessToken {
        return processApiResponse {
            tokenService.refreshAccessToken(BuildConfig.DOUYIN_KEY, refreshToken)
        }
    }

    suspend fun getRemotePersonalInfo(accessToken: String, openID: String): PersonalInfo {
        return processApiResponse {
            personalService.getPersonalInfo(accessToken, openID)
        }
    }
}

class AccountLocalDataSource @Inject constructor(
    @ApplicationContext context: Context
) : ILocalDataSource {
    private val secureSharedPref = context.secureSharedPref

    @set:JvmName("saveLocalClientToken")
    var localClientToken: ClientToken?
        get() {
            return secureSharedPref.getString(KEY_CLIENT_TOKEN, null)?.fromJson()
        }
        set(value) {
            secureSharedPref.edit {
                putString(KEY_CLIENT_TOKEN, value!!.toJson())
            }
        }

    @set:JvmName("saveLocalAccessToken")
    var localAccessToken: AccessToken?
        get() {
            return secureSharedPref.getString(KEY_ACCESS_TOKEN, null)?.fromJson()
        }
        set(value) {
            secureSharedPref.edit {
                putString(KEY_ACCESS_TOKEN, value!!.toJson())
            }
        }

    @set:JvmName("saveLocalPersonalInfo")
    var localPersonalInfo: PersonalInfo?
        get() {
            return secureSharedPref.getString(KEY_PERSONAL_INFO, null)?.fromJson()
        }
        set(value) {
            secureSharedPref.edit {
                putString(KEY_PERSONAL_INFO, value!!.toJson())
            }
        }
}