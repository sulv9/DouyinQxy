package com.qxy.lib.base.base.view.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qxy.lib.base.util.GenericUtil

/**
 * 同时封装了viewModel和binding的Activity基类
 */
abstract class BaseVmFragment<VM: ViewModel>: BaseFragment() {

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