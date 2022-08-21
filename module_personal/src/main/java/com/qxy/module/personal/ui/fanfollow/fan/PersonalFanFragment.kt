package com.qxy.module.personal.ui.fanfollow.fan

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qxy.lib.base.base.view.fragment.BaseVmBindFragment
import com.qxy.lib.base.ext.observe
import com.qxy.lib.common.network.processView
import com.qxy.module.personal.databinding.FragmentPersonalFanBinding
import com.qxy.module.personal.ui.fanfollow.follow.PersonalFollowRecyclerAdapter

class PersonalFanFragment private constructor() :
    BaseVmBindFragment<PersonalFanViewModel, FragmentPersonalFanBinding>(){

    private var mDataLength = 0

    private val mAdapter by lazy { PersonalFanRecyclerAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.initialize()
    }

    private fun initData() {
        observe(viewModel.fanData) { result ->
            result.processView(
                binding.personalFanPageLoading.root,
                binding.personalFanPageLoadError.root,
                binding.personalFanClContent
            ) {
                initViewOnce()
                binding.personalFanTvMineFollow.text = "我的粉丝（${it.first().total}）"
                val followingList = it.map { following ->
                    following.list ?: emptyList()
                }.flatten()
                val newList = listOf(mAdapter.currentList, followingList).flatten()
                mAdapter.submitList(newList)
                if (mDataLength != 0) binding.personalFanRv.scrollToPosition(mDataLength - 1)
                mDataLength = newList.size
            }
        }
    }

    private var isInitView = false
    private fun initViewOnce() {
        if (isInitView) return
        with(binding.personalFanRv) {
            val layoutManager = LinearLayoutManager(requireContext())
            this.layoutManager = layoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (layoutManager.findLastVisibleItemPosition() == mDataLength - 2) {
                        viewModel.fetchMoreFanData(isRefresh = false)
                    }
                }
            })
            adapter = mAdapter
        }
        isInitView = true
    }

    companion object {
        fun newInstance() = PersonalFanFragment()
    }
}