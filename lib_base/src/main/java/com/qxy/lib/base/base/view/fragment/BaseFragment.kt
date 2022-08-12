package com.qxy.lib.base.base.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qxy.lib.base.ext.log
import com.qxy.lib.base.base.view.IView

/**
 * Fragment基类，如果不需要使用binding和viewModel可以选择继承该类
 */
abstract class BaseFragment: Fragment(), IView {

    /**
     * 是否打印生命周期
     * 如果为true，请重写[TAG]属性
     */
    protected open val isLogLifecycle = false

    protected open val TAG = this::class.java

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (isLogLifecycle) log { "$TAG -> onAttach" }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isLogLifecycle) log { "$TAG -> onCreate" }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (isLogLifecycle) log { "$TAG -> onCreateView" }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isLogLifecycle) log { "$TAG -> onViewCreated" }
    }

    override fun onStart() {
        super.onStart()
        if (isLogLifecycle) log { "$TAG -> onStart" }
    }

    override fun onResume() {
        super.onResume()
        if (isLogLifecycle) log { "$TAG -> onResume" }
    }

    override fun onPause() {
        super.onPause()
        if (isLogLifecycle) log { "$TAG -> onPause" }
    }

    override fun onStop() {
        super.onStop()
        if (isLogLifecycle) log { "$TAG -> onStop" }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isLogLifecycle) log { "$TAG -> onDestroyView" }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isLogLifecycle) log { "$TAG -> onDestroy" }
    }

    override fun onDetach() {
        super.onDetach()
        if (isLogLifecycle) log { "$TAG -> onDetach" }
    }

}