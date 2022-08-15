package com.qxy.module.rank.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.qxy.lib.base.base.view.fragment.BaseBindFragment
import com.qxy.lib.base.ext.observe
import com.qxy.lib.base.util.args
import com.qxy.lib.common.R
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
        when {
            state.isLoading -> {
                // 加载中
                binding.rankListPageLoading.root.visibility = View.VISIBLE
            }
            state.networkError != null -> {
                // 网络错误
                binding.rankListPageLoading.root.visibility = View.GONE
                binding.rankListPageNetworkError.root.visibility = View.VISIBLE
            }
            state.apiError != null -> {
                // Api请求错误
                binding.rankListPageLoading.root.visibility = View.GONE
                binding.rankListPageLoadError.root.visibility = View.VISIBLE
                binding.rankListPageLoadError.root.findViewById<TextView>(
                    R.id.common_tv_load_fail
                ).text = state.apiError.message
            }
            state.emptyError != null -> {
                // 空数据
                binding.rankListPageLoading.root.visibility = View.GONE
                binding.rankListPageLoadError.root.visibility = View.VISIBLE
                binding.rankListPageLoadError.root.findViewById<TextView>(
                    R.id.common_tv_load_fail
                ).text = state.emptyError.message
            }
            state.unknownError != null -> {
                // 未知错误
                binding.rankListPageLoading.root.visibility = View.GONE
                binding.rankListPageLoadError.root.visibility = View.VISIBLE
                binding.rankListPageLoadError.root.findViewById<TextView>(
                    R.id.common_tv_load_fail
                ).text = state.unknownError.message
            }
            state.rankList != null -> {
                // 展示数据
                binding.rankListPageLoading.root.visibility = View.GONE
                initDataWhenSuccess(state)
            }
        }
    }

    private fun initDataWhenSuccess(state: RankViewState) {
        // 初始化recyclerview
        binding.rankListRv.visibility = View.VISIBLE
        binding.rankListRv.layoutManager = LinearLayoutManager(requireActivity())
        binding.rankListRv.adapter = rankListAdapter
        rankListAdapter.submitList(state.rankList)
        binding.rankListTvInfo.setOnClickListener {
            // TODO
        }
    }

    companion object {
        fun newInstance(type: Int) = RankListFragment().apply {
            this.type = type
        }
    }

}