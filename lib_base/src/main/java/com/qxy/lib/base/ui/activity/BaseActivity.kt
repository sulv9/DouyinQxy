package com.qxy.lib.base.ui.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qxy.lib.base.ui.BaseView

/**
 * Activity基类，如果不需要使用binding和viewModel可以选择继承该类
 */
abstract class BaseActivity : AppCompatActivity(), BaseView {

    /**
     * 是否锁定屏幕为竖屏
     */
    protected val isPortraitScreen: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isPortraitScreen) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

}