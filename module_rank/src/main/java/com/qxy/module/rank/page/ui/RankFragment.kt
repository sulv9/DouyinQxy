package com.qxy.module.rank.page.ui

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.qxy.lib.base.ext.log
import com.qxy.lib.base.ui.fragment.BaseVmBindFragment
import com.qxy.lib.common.config.RouteTable
import com.qxy.module.rank.databinding.FragmentRankBinding
import com.qxy.module.rank.page.viewmodel.RankViewModel

@Route(path = RouteTable.RANK_ENTRY)
class RankFragment : BaseVmBindFragment<RankViewModel, FragmentRankBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        log { "Rank View Created" }
    }

}