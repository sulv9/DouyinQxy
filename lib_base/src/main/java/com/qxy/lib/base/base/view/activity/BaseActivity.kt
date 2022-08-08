package com.qxy.lib.base.base.view.activity

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import com.qxy.lib.base.base.view.IView

/**
 * Activity基类，如果不需要使用binding和viewModel可以选择继承该类
 */
abstract class BaseActivity : AppCompatActivity(), IView {

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
     * Activity是否重建
     */
    protected var mIsActivityRecreated = false
        private set

    /**
     * 替换Fragment的正确写法
     * 防止onCreate中重复replaceFragment
     * 注意这里的[F]不是实际的类型
     */
    protected inline fun <reified F : Fragment> replaceFragment(
        @IdRes containerViewId: Int,
        targetFragment: () -> F
    ) {
        if (lifecycle.currentState == Lifecycle.State.CREATED && mIsActivityRecreated) {
            // 在onCreate中重建Activity时会自动恢复Fragment，不需要重复replaceFragment
            return
        }
        supportFragmentManager.commit {
            replace(containerViewId, targetFragment.invoke())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mIsActivityRecreated = savedInstanceState != null
        super.onCreate(savedInstanceState)

        if (isPortraitScreen) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        if (isStatusBarTransparent) {
            window.statusBarColor = Color.TRANSPARENT
        }
    }

}