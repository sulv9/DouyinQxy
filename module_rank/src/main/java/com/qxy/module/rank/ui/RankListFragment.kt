package com.qxy.module.rank.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.qxy.lib.base.base.view.fragment.BaseBindFragment
import com.qxy.lib.base.ext.log
import com.qxy.lib.base.ext.observe
import com.qxy.lib.base.util.args
import com.qxy.module.rank.databinding.FragmentRankListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankListFragment : BaseBindFragment<FragmentRankListBinding>() {

    private var type: Int by args()

    private val viewModel: RankViewModel by viewModels()

    private lateinit var rankListAdapter: RankRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rankListAdapter = RankRecyclerAdapter()
        observe(viewModel.rankData, this::showRankView)
    }

    override fun onResume() {
        super.onResume()
        viewModel.initialize(type) // Fragment可见后才开始初始化数据，通过变量控制初始化次数
    }

    private fun showRankView(state: RankViewState) {
        log { "state changed: $state" }
        binding.rankListPageLoading.root.visibility =
            if (state.isLoading) View.VISIBLE else View.GONE
        binding.rankListPageLoadFail.root.visibility =
            if (state.isApiError) View.VISIBLE else View.GONE
        binding.rankListPageEmptyError.root.visibility =
            if (state.isEmptyError) View.VISIBLE else View.GONE
        if (state.rankList.isNullOrEmpty()) {
            binding.rankListRv.visibility = View.GONE
        } else {
            binding.rankListRv.visibility = View.VISIBLE
            binding.rankListRv.adapter = rankListAdapter
            rankListAdapter.submitList(state.rankList)
        }
    }

    companion object {
        fun newInstance(type: Int) = RankListFragment().apply {
            this.type = type
        }
    }

}