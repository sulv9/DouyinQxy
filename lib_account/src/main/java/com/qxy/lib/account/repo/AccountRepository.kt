package com.qxy.lib.account.repo

import android.content.Context
import androidx.core.content.edit
import com.qxy.lib.account.*
import com.qxy.lib.account.ext.secureSharedPref
import com.qxy.lib.account.model.ClientToken
import com.qxy.lib.account.network.TokenService
import com.qxy.lib.base.BuildConfig
import com.qxy.lib.base.base.network.Errors
import com.qxy.lib.base.base.repository.BaseRepositoryBoth
import com.qxy.lib.base.base.repository.ILocalDataSource
import com.qxy.lib.base.base.repository.IRemoteDataSource
import com.qxy.lib.base.util.fromJson
import com.qxy.lib.base.util.toJson
import com.qxy.lib.common.network.processApiResponse
import dagger.hilt.android.qualifiers.ApplicationContext
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
        val localClientToken = localDataSource.getLocalClientToken()
        localClientToken?.let {
            if (!isTokenExpire(it.responseTime, it.expiresIn)) return it.accessToken
        }
        val remoteClientToken = remoteDataSource.getRemoteClientToken()
        localDataSource.saveLocalClientToken(remoteClientToken.copy(responseTime = System.currentTimeMillis()))
        return remoteClientToken.accessToken
    }

    suspend fun getAccessToken(): String {
        val savedTimestamp =
            localDataSource.secureSharedPref.getLong(ACCESS_TOKEN_SAVED_TIMESTAMP, 0)
        val savedAccessToken =
            localDataSource.secureSharedPref.getString(ACCESS_TOKEN, null)
        val savedExpire =
            localDataSource.secureSharedPref.getInt(ACCESS_TOKEN_EXPIRES_IN, 0)

        return if (savedAccessToken != null && !isTokenExpire(savedTimestamp, savedExpire)) {
            savedAccessToken
        } else {
            val authCode = localDataSource.secureSharedPref.getString(USER_AUTH_CODE, "") ?: ""
            val remote = remoteDataSource.tokenService.getAccessToken(
                BuildConfig.DOUYIN_SECRET, authCode, BuildConfig.DOUYIN_KEY
            )
            remote.data?.let { data ->
                localDataSource.secureSharedPref.edit {
                    putLong(ACCESS_TOKEN_SAVED_TIMESTAMP, System.currentTimeMillis())
                    putString(ACCESS_TOKEN, data.accessToken)
                    putInt(ACCESS_TOKEN_EXPIRES_IN, data.expiresIn)
                }
                data.accessToken
            } ?: throw IllegalStateException("access token must not be null.")
        }
    }

    private fun isTokenExpire(savedTimestamp: Long, expireIn: Int): Boolean {
        return System.currentTimeMillis() - savedTimestamp > expireIn * 1000
    }

    private fun isTokenExpire(savedTimestamp: Long, expireIn: Long): Boolean {
        return System.currentTimeMillis() - savedTimestamp > expireIn * 1000
    }

}

class AccountRemoteDataSource @Inject constructor(
    val tokenService: TokenService
) : IRemoteDataSource {
    suspend fun getRemoteClientToken(): ClientToken {
        return processApiResponse {
            tokenService.getClientToken(BuildConfig.DOUYIN_KEY, BuildConfig.DOUYIN_SECRET)
        }
    }
}

class AccountLocalDataSource @Inject constructor(
    @ApplicationContext context: Context
) : ILocalDataSource {
    val secureSharedPref = context.secureSharedPref

    fun getLocalClientToken(): ClientToken? {
        return secureSharedPref.getString(KEY_CLIENT_TOKEN, null)?.fromJson()
    }

    fun saveLocalClientToken(clientToken: ClientToken) {
        secureSharedPref.edit {
            putString(KEY_CLIENT_TOKEN, clientToken.toJson())
        }
    }

}