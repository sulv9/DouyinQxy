package com.qxy.codeerror

import com.alibaba.android.arouter.launcher.ARouter
import com.qxy.lib.base.BaseApp
import dagger.hilt.android.HiltAndroidApp

/**
 * HiltApp必须依赖所有使用hilt的模块
 */
@HiltAndroidApp
class MainApplication : BaseApp() {
    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
//        DouY
    }
}