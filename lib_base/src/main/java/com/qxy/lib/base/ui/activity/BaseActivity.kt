package com.qxy.lib.base.ui.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qxy.lib.base.ui.BaseView

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