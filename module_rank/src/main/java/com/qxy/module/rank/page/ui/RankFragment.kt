package com.qxy.module.rank.page.ui

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.qxy.lib.base.ext.getStatusBarHeight
import com.qxy.lib.base.ui.fragment.BaseVmBindFragment
import com.qxy.lib.common.config.RouteTable
import com.qxy.module.rank.databinding.FragmentRankBinding
import com.qxy.module.rank.page.viewmodel.RankViewModel

@Route(path = RouteTable.RANK_ENTRY)
class RankFragment : BaseVmBindFragment<RankViewModel, FragmentRankBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setViewTopMargin()
    }

    /**
     * 设置布局，使用沉浸式状态栏
     */
    private fun setViewTopMargin() {
        // 设置根布局topMargin为负，以屏蔽AppBarLayout的状态栏问题
        ViewCompat.setOnApplyWindowInsetsListener(binding.rankClParent) { view, insets ->
            val params = view.layoutParams as FrameLayout.LayoutParams
            params.topMargin = -insets.systemWindowInsetTop
            insets
        }
        // 重置AppBarLayout的高度
        binding.rankAppBar.post {
            val params = binding.rankAppBar.layoutParams as CoordinatorLayout.LayoutParams
            val height = params.height
            params.height = height + requireActivity().getStatusBarHeight()
            binding.rankAppBar.layoutParams = params
        }
        // 设置切换榜单TopMargin
        val params = binding.rankTvChangeRank.layoutParams as ConstraintLayout.LayoutParams
        params.topMargin = requireActivity().getStatusBarHeight()
        binding.rankTvChangeRank.layoutParams = params
    }

}