package com.qxy.module.personal.ui.fanfollow.follow

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qxy.lib.base.base.view.fragment.BaseVmBindFragment
import com.qxy.lib.base.ext.observe
import com.qxy.lib.common.network.processView
import com.qxy.module.personal.databinding.FragmentPersonalFollowBinding

class PersonalFollowFragment private constructor():
    BaseVmBindFragment<PersonalFollowViewModel, FragmentPersonalFollowBinding>() {

    private var mDataLength = 0

    private val mAdapter by lazy { PersonalFollowRecyclerAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.initialize()
    }

    private fun initData() {
        observe(viewModel.followingData) { result ->
            result.processView(
                binding.personalFollowPageLoading.root,
                binding.personalFollowPageLoadError.root,
                binding.personalFollowClContent
            ) {
                initViewOnce()
                binding.personalFollowTvMineFans.text = "我的关注"
                val followingList = it.map { following ->
                    following.list ?: emptyList()
                }.flatten()
                val newList = listOf(mAdapter.currentList, followingList).flatten()
                mAdapter.submitList(newList)
                if (mDataLength != 0) binding.personalFollowRv.scrollToPosition(mDataLength - 1)
                mDataLength = newList.size
            }
        }
    }

    private var isInitView = false
    private fun initViewOnce() {
        if (isInitView) return
        with(binding.personalFollowRv) {
            val layoutManager = LinearLayoutManager(requireContext())
            this.layoutManager = layoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (layoutManager.findLastVisibleItemPosition() == mDataLength - 2) {
                        viewModel.fetchMoreFollowingData(isRefresh = false)
                    }
                }
            })
            adapter = mAdapter
        }
        isInitView = true
    }

    companion object {
        fun newInstance() = PersonalFollowFragment()
    }
}