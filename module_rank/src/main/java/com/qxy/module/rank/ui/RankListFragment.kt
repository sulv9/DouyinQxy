package com.qxy.module.rank.ui

import android.os.Bundle
import android.view.View
import com.qxy.lib.base.base.view.fragment.BaseBindFragment
import com.qxy.lib.base.util.args
import com.qxy.module.rank.databinding.FragmentRankListBinding

class RankListFragment : BaseBindFragment<FragmentRankListBinding>() {

    private var type: Int by args()

    companion object {
        fun newInstance(type: Int) = RankListFragment().apply {
            this.type = type
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}