package com.qxy.lib.account

import android.content.Context
import androidx.core.content.edit
import com.qxy.lib.account.ext.secureSharedPref
import com.qxy.lib.account.network.TokenService
import com.qxy.lib.base.BuildConfig
import com.qxy.lib.base.base.repository.BaseRepositoryBoth
import com.qxy.lib.base.base.repository.ILocalDataSource
import com.qxy.lib.base.base.repository.IRemoteDataSource
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
    suspend fun getAccessToken(): String {
        val savedTimestamp =
            localDataSource.secureSharedPref.getLong(ACCESS_TOKEN_SAVED_TIMESTAMP, 0)
        val savedAccessToken =
            localDataSource.secureSharedPref.getString(ACCESS_TOKEN, null)
        val savedExpire =
            localDataSource.secureSharedPref.getInt(ACCESS_TOKEN_EXPIRES_IN, 0)

        return if (savedAccessToken != null && !accessTokenExpire(savedTimestamp, savedExpire)) {
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

    private fun accessTokenExpire(savedTimestamp: Long, expire: Int): Boolean {
        return System.currentTimeMillis() - savedTimestamp > expire * 1000
    }
}

class AccountRemoteDataSource @Inject constructor(
    val tokenService: TokenService
) : IRemoteDataSource

class AccountLocalDataSource @Inject constructor(
    @ApplicationContext context: Context
) : ILocalDataSource {
    val secureSharedPref = context.secureSharedPref
}