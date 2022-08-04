package com.qxy.lib.account.service

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.facade.annotation.Route
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi
import com.qxy.api.account.IAccountService
import com.qxy.lib.account.USER_AUTH_CODE
import com.qxy.lib.account.USER_IS_LOGIN
import com.qxy.lib.account.ext.secureSharedPref
import com.qxy.lib.base.ext.clear
import com.qxy.lib.common.config.RouteTable


@Route(path = RouteTable.ACCOUNT_SERVICE)
class AccountServiceImpl : IAccountService {

    private val accountLiveData = MutableLiveData<IAccountService.LoginState>()

    private lateinit var secureSharedPref: SharedPreferences

    override fun init(context: Context) {
        secureSharedPref = context.secureSharedPref
        accountLiveData.observeForever {
            when {
                it.isLogIn -> {
                    // 登录成功，保存authCode
                    secureSharedPref.edit {
                        putBoolean(USER_IS_LOGIN, true)
                        putString(USER_AUTH_CODE, it.authCode)
                    }
                }
                it.isLogFail || it.isLogOut -> {
                    // 登录失败或退出登录，清除用户数据
                    secureSharedPref.clear()
                }
            }
        }
    }

    override fun getAccountLiveData() = accountLiveData

    override fun isLogin() = secureSharedPref.getBoolean(USER_IS_LOGIN, false)

    override fun login(douYinOpenApi: DouYinOpenApi) {
        val request = Authorization.Request()
        request.scope = "user_info,trial.whitelist" // 用户授权时必选权限
        request.state = "ww" // 用于保持请求和回调的状态，授权请求后原样带回给第三方。
        douYinOpenApi.authorize(request) // 优先使用抖音app进行授权，如果抖音app因版本或者其他原因无法授权，则使用wap页授权
    }

    override fun responseLoginInfo(response: Authorization.Response) {
        if (response.isSuccess) {
            accountLiveData.value = IAccountService.LoginState(
                isLogIn = true,
                authCode = response.authCode
            )
        } else {
            accountLiveData.value = IAccountService.LoginState(
                isLogFail = true,
                errorMsg = response.errorMsg
            )
        }
    }

}