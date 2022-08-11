package com.qxy.api.account

import com.qxy.lib.account.AccountLocalDataSource
import com.qxy.lib.account.AccountRemoteDataSource
import com.qxy.lib.account.AccountRepository
import javax.inject.Inject

/**
 * @project DouyinQxy
 * @author Yenaly Liew
 * @time 2022/08/11 011 15:40
 */
class AccountRepository @Inject constructor(
    private val remoteDataSource: AccountRemoteDataSource,
    private val localDataSource: AccountLocalDataSource
) {
    suspend fun getAccessToken() =
        AccountRepository(remoteDataSource, localDataSource).getAccessToken()
}