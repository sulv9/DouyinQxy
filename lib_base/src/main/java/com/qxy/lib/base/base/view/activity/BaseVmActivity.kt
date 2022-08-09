package com.qxy.lib.base.base.view.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qxy.lib.base.util.GenericUtil

/**
 * 封装了viewModel的Activity基类
 */
abstract class BaseVmActivity<VM : ViewModel> : BaseActivity() {

    protected open fun getViewModelFactory(): ViewModelProvider.Factory? = null

    @Suppress("UNCHECKED_CAST")
    protected val viewModel by lazy {
        val factory = getViewModelFactory()
        if (factory == null) {
            ViewModelProvider(this)[GenericUtil.getGenericFromSuper(javaClass)] as VM
        } else {
            ViewModelProvider(
                this,
                factory
            )[GenericUtil.getGenericFromSuper(javaClass)] as VM
        }
    }

}