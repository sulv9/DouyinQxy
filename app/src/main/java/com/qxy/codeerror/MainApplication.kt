package com.qxy.codeerror

import com.alibaba.android.arouter.launcher.ARouter
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory
import com.bytedance.sdk.open.douyin.DouYinOpenConfig
import com.qxy.lib.base.BaseApp
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * HiltApp必须依赖所有使用hilt的模块
 */
@HiltAndroidApp
class MainApplication : BaseApp() {
    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
        DouYinOpenApiFactory.init(DouYinOpenConfig(BuildConfig.DOUYIN_KEY))
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
            Timber.plant(Timber.DebugTree())
        }
    }
}