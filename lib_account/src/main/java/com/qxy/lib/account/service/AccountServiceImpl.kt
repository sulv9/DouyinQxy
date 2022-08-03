package com.qxy.lib.account.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.qxy.api.account.IAccountService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Route(path = "/account/service")
class AccountServiceImpl @Inject constructor(
    @ApplicationContext context: ApplicationContext
) : IAccountService {
    override fun login() {
        TODO("Not yet implemented")
    }

    override fun init(context: Context?) {
        TODO("Not yet implemented")
    }
}