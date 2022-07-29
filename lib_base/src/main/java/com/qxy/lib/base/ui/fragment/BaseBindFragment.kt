package com.qxy.lib.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.qxy.lib.base.util.GenericUtil

abstract class BaseBindFragment<VB : ViewBinding> : BaseFragment() {

    /**
     * Fragment 的存在时间比其视图长
     * 在 Fragment 的 [onDestroyView] 方法中清除对绑定类实例的所有引用，防止内存泄漏
     */
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 反射获取binding
        val method = GenericUtil.getGenericFromSuper<VB, ViewBinding>(javaClass).getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        _binding = method.invoke(null, inflater, container, false) as VB
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}