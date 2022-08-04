package com.qxy.api.account

import androidx.lifecycle.LiveData
import com.alibaba.android.arouter.facade.template.IProvider
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi

interface IAccountService : IProvider {

    fun getAccountLiveData(): LiveData<LoginState>

    /**
     * 是否已登录
     */
    fun isLogin(): Boolean

    /**
     * 登录授权
     */
    fun login(douYinOpenApi: DouYinOpenApi)

    /**
     * 响应登录授权结果
     */
    fun responseLoginInfo(response: Authorization.Response)

    data class LoginState(
        // 登录成功
        val isLogIn: Boolean = false,
        // 登出成功
        val isLogOut: Boolean = false,
        // 登录失败
        val isLogFail: Boolean = false,
        // 授权码
        val authCode: String = "",
        // 错误信息
        val errorMsg: String = "",
    )

}