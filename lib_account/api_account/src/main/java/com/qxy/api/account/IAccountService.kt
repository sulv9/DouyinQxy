package com.qxy.api.account

import android.content.SharedPreferences
import androidx.lifecycle.LifecycleOwner
import com.alibaba.android.arouter.facade.template.IProvider
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi

interface IAccountService : IProvider {

    /**
     * 监听登录状态的改变
     */
    fun observeLoginState(
        lifecycleOwner: LifecycleOwner,
        listener: (liveData: LoginState) -> Unit
    )

    /**
     * 监听当前是否需要切换Fragment
     */
    fun observeFragmentChange(
        lifecycleOwner: LifecycleOwner,
        listener: (liveData: LoginAction) -> Unit
    )

    /**
     * 发送切换界面请求
     * 一般指从登录成功后向主界面申请从登录界面切换到当前用户所选择的Tab界面
     * @param action 登录行为，取值见[LoginAction]
     */
    fun sendChangeFragmentRequest(action: LoginAction)

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

    suspend fun getAccessToken(): String

    suspend fun getClientToken(): String

    val openID: String

    val sharedPref: SharedPreferences

    /**
     * 登录行为
     */
    data class LoginAction(
        // 登入
        val logIn: Boolean = false,
        // 登出
        val logOut: Boolean = false
    )

    /**
     * 登录状态
     */
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