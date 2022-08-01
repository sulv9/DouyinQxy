package com.qxy.codeerror.douyinapi

import android.content.Intent
import android.os.Bundle
import com.bytedance.sdk.open.aweme.CommonConstants
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.aweme.common.handler.IApiEventHandler
import com.bytedance.sdk.open.aweme.common.model.BaseReq
import com.bytedance.sdk.open.aweme.common.model.BaseResp
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi
import com.qxy.lib.base.ext.log
import com.qxy.lib.base.ui.activity.BaseActivity

class DouYinEntryActivity : BaseActivity(), IApiEventHandler {

    private lateinit var mDouYinOpenApi: DouYinOpenApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDouYinOpenApi = DouYinOpenApiFactory.create(this)
        mDouYinOpenApi.handleIntent(intent, this)
    }

    override fun onReq(req: BaseReq?) {
    }

    override fun onResp(resq: BaseResp?) {
        // 授权成功获取AuthCode
        if (CommonConstants.ModeType.SEND_AUTH_RESPONSE == resq?.type) {
            val response = resq as Authorization.Response
            if (response.isSuccess) {
                log { "Response Success authCode:${response.authCode} grant:${response.grantedPermissions}" }
            } else {
                log { "Response Fail ${response.errorMsg}" }
            }
            finish()
        }
    }

    override fun onErrorIntent(intent: Intent?) {
        log { "Error Intent" }
        finish()
    }
}