package com.qxy.module.personal.ui.personal

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.qxy.lib.base.base.view.fragment.BaseVmBindFragment
import com.qxy.module.personal.databinding.FragmentPersonalVideoBinding

/**
 * @project DouyinQxy
 * @author Yenaly Liew
 * @time 2022/08/21 021 19:13
 */
class PersonalVideoFragment :
    BaseVmBindFragment<PersonalViewModel, FragmentPersonalVideoBinding>() {

    private val adapter by lazy(LazyThreadSafetyMode.NONE) { PersonalVideoListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.recyclerView.adapter = adapter

        flowObserve()
    }

    private fun flowObserve() {

    }
}