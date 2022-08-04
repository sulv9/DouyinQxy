package com.qxy.lib.base.ui.activity

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.qxy.lib.base.ui.BaseView

/**
 * Activity基类，如果不需要使用binding和viewModel可以选择继承该类
 */
abstract class BaseActivity : AppCompatActivity(), BaseView {

    /**
     * 是否锁定屏幕为竖屏
     */
    protected open val isPortraitScreen: Boolean = true

    /**
     * 是否为透明状态栏
     * 请配合根布局为[androidx.coordinatorlayout.widget.CoordinatorLayout]来使用
     */
    protected open val isStatusBarTransparent: Boolean = false

    /**
     * 替代Fragment
     * 如果
     */
    protected inline fun <reified F : Fragment> replaceFragment(
        @IdRes containerViewId: Int,
        newFragment: () -> F
    ) {
        var fragment = supportFragmentManager.findFragmentById(containerViewId)
        if (fragment !is F) {
            fragment = newFragment.invoke()
            supportFragmentManager.commit {
                replace(containerViewId, fragment)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isPortraitScreen) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        if (isStatusBarTransparent) {
            window.statusBarColor = Color.TRANSPARENT
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

}