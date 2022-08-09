package com.qxy.module.rank.ui

import android.os.Bundle
import android.view.View
import com.qxy.lib.base.base.view.fragment.BaseVmBindFragment
import com.qxy.lib.base.base.viewmodel.BaseViewModelFactory
import com.qxy.lib.base.util.args
import com.qxy.module.rank.databinding.FragmentRankListBinding

class RankListFragment : BaseVmBindFragment<RankViewModel, FragmentRankListBinding>() {

    private var type: Int by args()

    override fun getViewModelFactory() = BaseViewModelFactory { type }

    companion object {
        fun newInstance(type: Int) = RankListFragment().apply {
            this.type = type
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}