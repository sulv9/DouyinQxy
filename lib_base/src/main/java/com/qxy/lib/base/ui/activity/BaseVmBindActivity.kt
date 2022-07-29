package com.qxy.lib.base.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.qxy.lib.base.util.GenericUtil

abstract class BaseVmBindActivity<VM: ViewModel, VB: ViewBinding>: BaseBindActivity<VB>(){

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