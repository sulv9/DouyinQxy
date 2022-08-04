package com.qxy.lib.base.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.qxy.lib.base.util.GenericUtil

/**
 * 封装了binding的Activity基类
 */
abstract class BaseBindActivity<VB : ViewBinding> : BaseActivity() {

    /**
     * 在[setContentView]方法前执行的方法
     */
    protected open fun onBeforeSetContentView() {}

    /**
     * 初始化view
     */
    protected open fun initView() {}

    /**
     * 初始化data
     */
    protected open fun initData() {}

    /**
     * 利用反射初始化binding
     */
    @Suppress("UNCHECKED_CAST")
    protected val binding: VB by lazy {
        val method = GenericUtil.getGenericFromSuper<VB, ViewBinding>(javaClass)
            .getMethod("inflate", LayoutInflater::class.java)
        method.invoke(null, layoutInflater) as VB
    }

    /**
     * 不要重写该方法
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBeforeSetContentView()
        setContentView(binding.root)
        initView()
        initData()
    }

}