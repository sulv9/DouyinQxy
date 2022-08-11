package com.qxy.module.rank.ui

import android.os.Bundle
import android.view.View
import com.qxy.lib.base.base.view.fragment.BaseVmBindFragment
import com.qxy.lib.base.base.viewmodel.BaseViewModelFactory
import com.qxy.lib.base.ext.observe
import com.qxy.lib.base.util.args
import com.qxy.module.rank.data.repo.RankRepository
import com.qxy.module.rank.databinding.FragmentRankListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RankListFragment : BaseVmBindFragment<RankViewModel, FragmentRankListBinding>() {

    private var type: Int by args()

    @Inject
    lateinit var repo: RankRepository

    override fun getViewModelFactory() = BaseViewModelFactory { RankViewModel(type, repo) }

    companion object {
        fun newInstance(type: Int) = RankListFragment().apply {
            this.type = type
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.rankData, this::showRankView)
    }

    private fun showRankView(state: RankViewState) {

    }

}