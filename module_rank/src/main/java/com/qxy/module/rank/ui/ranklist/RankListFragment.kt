package com.qxy.module.rank.ui.ranklist

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.qxy.lib.base.base.network.Results
import com.qxy.lib.base.base.view.fragment.BaseBindFragment
import com.qxy.lib.base.ext.log
import com.qxy.lib.base.ext.observe
import com.qxy.lib.base.ext.toast
import com.qxy.lib.base.util.args
import com.qxy.lib.common.R
import com.qxy.module.rank.data.model.RankItem
import com.qxy.module.rank.databinding.FragmentRankListBinding
import com.qxy.module.rank.ui.rankversion.RankVersionDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankListFragment private constructor() : BaseBindFragment<FragmentRankListBinding>() {

    private var type: Int by args()

    private val viewModel: RankListViewModel by viewModels()

    private lateinit var rankListAdapter: RankRecyclerAdapter

    private var mLastState: Results.State = Results.State.LOADING

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rankListAdapter = RankRecyclerAdapter()
        observe(viewModel.rankData, this::showRankView)
    }

    override fun onResume() {
        super.onResume()
        viewModel.initialize(type) // Fragment可见后才开始初始化数据，通过变量控制初始化次数
    }

    private fun showRankView(result: Results<List<RankItem>>) {
        when (result.state) {
            is Results.State.LOADING -> {
//                log { ">>>0" }
                // 加载中
                binding.rankListPageLoadError.root.visibility = View.GONE
                binding.rankListPageLoading.root.visibility = View.VISIBLE
                binding.rankListPageLoading.root.findViewById<CircularProgressIndicator>(
                    R.id.common_cpi_loading
                ).isIndeterminate = true
                mLastState = Results.State.LOADING
            }
            is Results.State.ERROR -> {
//                log { ">>>1 ${(result.state as Results.State.ERROR).errors}" }
                val errorState = result.state as Results.State.ERROR
                // 获取较高优先级的错误信息
                val errorMsg = (if (mLastState is Results.State.ERROR
                    && (mLastState as Results.State.ERROR).errors.level < errorState.errors.level
                ) (mLastState as Results.State.ERROR).errors.message
                else errorState.errors.message) ?: "Error"
                // 显示错误信息
                binding.rankListPageLoading.root.visibility = View.GONE
                binding.rankListPageLoadError.root.visibility = View.VISIBLE
                binding.rankListPageLoadError.root.findViewById<TextView>(
                    R.id.common_tv_load_fail
                ).text = errorMsg
                if (mLastState is Results.State.SUCCESS) {
                    toast(errorMsg)
                }
                mLastState = Results.State.ERROR(errorState.errors)
            }
            is Results.State.SUCCESS -> {
//                log { ">>>2" }
                // 如果请求错误先抛出，则将其以toast形式展示
                if (mLastState is Results.State.ERROR) {
                    binding.rankListPageLoadError.root.visibility = View.GONE
                    toast((mLastState as Results.State.ERROR).errors.message ?: "Error")
                }
                // 展示数据
                binding.rankListPageLoading.root.visibility = View.GONE
                initDataWhenSuccess(result.data!!)
                mLastState = Results.State.SUCCESS
            }
            is Results.State.NONE -> {}
        }
    }

    private fun initDataWhenSuccess(rankList: List<RankItem>) {
        // 初始化recyclerview
        binding.rankListClContent.visibility = View.VISIBLE
        binding.rankListRv.layoutManager = LinearLayoutManager(requireActivity())
        binding.rankListRv.adapter = rankListAdapter
        rankListAdapter.submitList(rankList)
        // 初始化榜单版本信息
        binding.rankListTvInfo.setOnClickListener {
            val modalRankVersion = RankVersionDialogFragment.newInstance(type)
            // 设置回调
            childFragmentManager.setFragmentResultListener(
                RankVersionDialogFragment.REQUEST_KEY_VERSION,
                viewLifecycleOwner
            ) { requestKey, bundle ->
                val selectedVersion = bundle.getInt(
                    RankVersionDialogFragment.RESULT_VERSION_SELECTED
                )
                viewModel.fetchRankData(type, selectedVersion)
            }
            // 显示
            modalRankVersion.show(childFragmentManager, RankVersionDialogFragment.TAG)
        }
    }

    companion object {
        fun newInstance(type: Int) = RankListFragment().apply {
            this.type = type
        }
    }

}