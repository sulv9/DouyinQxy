package com.qxy.lib.base.base.view.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qxy.lib.base.util.GenericUtil

/**
 * 封装了viewModel的Activity基类
 */
abstract class BaseVmActivity<VM : ViewModel> : BaseActivity() {

    protected val mViewModelFactory: ViewModelProvider.Factory? = null

    @Suppress("UNCHECKED_CAST")
    protected val viewModel by lazy {
        if (mViewModelFactory == null) {
            ViewModelProvider(this)[GenericUtil.getGenericFromSuper(javaClass)] as VM
        } else {
            ViewModelProvider(
                this,
                mViewModelFactory
            )[GenericUtil.getGenericFromSuper(javaClass)] as VM
        }
    }

}