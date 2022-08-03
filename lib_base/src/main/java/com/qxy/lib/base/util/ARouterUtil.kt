@file:Suppress("UNCHECKED_CAST")

package com.qxy.lib.base.util

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

object ARouterUtil {

    /**
     * 根据路径[servicePath]获取服务的实现类
     */
    fun <T: Any> getService(servicePath: String): T {
        return ARouter.getInstance().build(servicePath).navigation() as T
    }

    /**
     * 获取Fragment实例
     */
    fun getFragment(fragPath: String, with: (Postcard.() -> Unit)? = null): Fragment {
        return ARouter.getInstance().build(fragPath).apply { with?.invoke(this) }
            .navigation() as Fragment
    }

    /**
     * 跳转到Activity
     */
    fun goActivity(activityPath: String, with: (Postcard.() -> Unit)? = null) {
        ARouter.getInstance().build(activityPath).apply { with?.invoke(this) }.navigation()
    }

}