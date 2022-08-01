package com.qxy.codeerror

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.sdk.open.aweme.authorize.model.Authorization
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi
import com.qxy.lib.base.ext.toast

class MainActivity : AppCompatActivity() {

    private lateinit var douYinOpenApi: DouYinOpenApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        douYinOpenApi = DouYinOpenApiFactory.create(this)
        findViewById<Button>(R.id.btn_send_auth).setOnClickListener {
            if (sendAuth()) toast("授权成功")
            else toast("授权失败")
        }
    }

    private fun sendAuth(): Boolean {
        val request = Authorization.Request()
        request.scope = "user_info,trial.whitelist" // 用户授权时必选权限
        request.state = "ww" // 用于保持请求和回调的状态，授权请求后原样带回给第三方。
        return douYinOpenApi.authorize(request) // 优先使用抖音app进行授权，如果抖音app因版本或者其他原因无法授权，则使用wap页授权
    }
}