package com.qxy.lib.base.ui.activity

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.qxy.lib.base.ext.log
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
     * TODO 待解释
     * 注意这里的[F]不是实际的类型
     */
    protected inline fun <reified F : Fragment> replaceFragment(
        @IdRes containerViewId: Int,
        generateFragment: () -> F
    ) {
        val oldFragment = supportFragmentManager.findFragmentById(containerViewId)
        val newFragment = generateFragment.invoke()
        if (oldFragment == null || oldFragment::class != newFragment::class) {
            supportFragmentManager.commit {
                replace(containerViewId, newFragment)
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
        }
    }

}