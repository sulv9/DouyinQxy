package com.qxy.module.rank.ui.ranklist

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.qxy.lib.base.base.network.Results
import com.qxy.lib.base.base.network.whenFailure
import com.qxy.lib.base.base.network.whenLoading
import com.qxy.lib.base.base.network.whenSuccess
import com.qxy.lib.base.base.view.fragment.BaseBindFragment
import com.qxy.lib.base.ext.observe
import com.qxy.lib.base.ext.toast
import com.qxy.lib.base.util.args
import com.qxy.lib.base.util.fromJson
import com.qxy.lib.common.R
import com.qxy.module.rank.data.model.RankItem
import com.qxy.module.rank.data.model.RankVersionItem
import com.qxy.module.rank.databinding.FragmentRankListBinding
import com.qxy.module.rank.ui.rankversion.RankVersionDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankListFragment private constructor() : BaseBindFragment<FragmentRankListBinding>() {

    private var type: Int by args()

    private val viewModel: RankListViewModel by viewModels()

    private lateinit var rankListAdapter: RankRecyclerAdapter

    private var mLastState: Results<List<RankItem>> = Results.Loading

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rankListAdapter = RankRecyclerAdapter()
        observe(viewModel.rankData, this::showRankView)
        // 初始化榜单版本点击事件
        binding.rankListTvInfo.setOnClickListener {
            val modalRankVersion = RankVersionDialogFragment.newInstance()
            // 设置回调
            childFragmentManager.setFragmentResultListener(
                RankVersionDialogFragment.REQUEST_KEY_VERSION,
                viewLifecycleOwner
            ) { requestKey, bundle ->
                val selectedVersion = bundle.getString(
                    RankVersionDialogFragment.RESULT_VERSION_SELECTED
                )?.fromJson<RankVersionItem>()
                viewModel.fetchRankData(selectedVersion)
            }
            // 显示
            modalRankVersion.show(childFragmentManager, RankVersionDialogFragment.TAG)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initialize(type) // Fragment可见后才开始初始化数据，通过变量控制初始化次数
    }

    private fun showRankView(state: RankListViewState) {
        val result = state.result
        result.whenLoading {
//            log { ">>>0" }
            // 加载中
            binding.rankListPageLoadError.root.visibility = View.GONE
            binding.rankListClContent.visibility = View.GONE
            binding.rankListPageLoading.root.visibility = View.VISIBLE
            binding.rankListPageLoading.root.findViewById<CircularProgressIndicator>(
                R.id.common_cpi_loading
            ).isIndeterminate = true
            mLastState = Results.Loading
        }
        result.whenFailure {
//            log { ">>>1 $it" }
            // 获取较高优先级的错误信息
            val errorMsg = (if (mLastState is Results.Failure
                && (mLastState as Results.Failure).errors.level < it.level
            ) (mLastState as Results.Failure).errors.message
            else it.message) ?: "Error"
            // 显示错误信息
            binding.rankListPageLoading.root.visibility = View.GONE
            if (mLastState is Results.Success) {
                toast(errorMsg)
            } else {
                binding.rankListPageLoadError.root.visibility = View.VISIBLE
                binding.rankListPageLoadError.root.findViewById<TextView>(
                    R.id.common_tv_load_fail
                ).text = errorMsg
            }
            mLastState = Results.Failure(it)
        }
        result.whenSuccess {
//            log { ">>>2 $it" }
            // 如果请求错误先抛出，则将其以toast形式展示
            if (mLastState is Results.Failure) {
                binding.rankListPageLoadError.root.visibility = View.GONE
                toast((mLastState as Results.Failure).errors.message ?: "Error")
            }
            // 展示数据
            binding.rankListPageLoading.root.visibility = View.GONE
            // 初始化recyclerview
            binding.rankListClContent.visibility = View.VISIBLE
            binding.rankListRv.layoutManager = LinearLayoutManager(requireActivity())
            binding.rankListRv.adapter = rankListAdapter
            rankListAdapter.submitList(it)
            binding.rankListTvInfo.text = state.rankInfo
            // 更新mLastState
            mLastState = Results.Success(it)
        }
    }

    companion object {
        fun newInstance(type: Int) = RankListFragment().apply {
            this.type = type
        }
    }

}