package com.qxy.api.account

import com.alibaba.android.arouter.facade.template.IProvider
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi

interface IAccountService : IProvider {

    /**
     * 登录授权
     */
    fun login(douYinOpenApi: DouYinOpenApi)

    /**
     * 响应登录授权结果
     */
    fun responseLoginInfo(response: Authorization.Response)

}