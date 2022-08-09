package com.qxy.module.rank.ui

import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import androidx.constraintlayout.widget.ConstraintLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayoutMediator
import com.qxy.lib.base.base.view.adapter.SimpleViewPagerAdapter
import com.qxy.lib.base.base.view.fragment.BaseBindFragment
import com.qxy.lib.base.ext.dp
import com.qxy.lib.base.ext.getStatusBarHeight
import com.qxy.lib.common.config.RouteTable
import com.qxy.module.rank.R
import com.qxy.module.rank.databinding.FragmentRankBinding

@Route(path = RouteTable.RANK_ENTRY)
class RankFragment : BaseBindFragment<FragmentRankBinding>() {

    private val typeWithName = mapOf(
        1 to getString(R.string.rank_title_movie),
        2 to getString(R.string.rank_title_tv),
        3 to getString(R.string.rank_title_show)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        setViewTopMargin()
        // 初始化ViewPager
        val pagerAdapter = SimpleViewPagerAdapter(childFragmentManager, lifecycle)
        typeWithName.forEach { (type, _) -> pagerAdapter.add { RankListFragment.newInstance(type) } }
        binding.rankVp.adapter = pagerAdapter
        // 离屏限制为1
        binding.rankVp.offscreenPageLimit = 1
        // viewpager联合tabLayout
        TabLayoutMediator(binding.rankTl, binding.rankVp) { tab, position ->
            tab.text = typeWithName[position + 1]
        }.attach()
    }

    /**
     * 设置布局，使用沉浸式状态栏
     */
    private fun setViewTopMargin() {
        // 将父布局内容延伸至状态栏
        binding.rankMlParent.systemUiVisibility = (SYSTEM_UI_FLAG_LAYOUT_STABLE
                or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        // 设置按钮偏移
        with(binding.rankTvChangeRank) {
            val params = layoutParams as ConstraintLayout.LayoutParams
            params.topMargin = requireActivity().getStatusBarHeight() + 10.dp
            layoutParams = params
        }
    }

}