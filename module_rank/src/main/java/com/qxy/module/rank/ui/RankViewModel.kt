package com.qxy.module.rank.ui

import com.qxy.lib.base.base.viewmodel.BaseViewModel
import com.qxy.module.rank.data.repo.RankRepository
import dagger.hilt.android.lifecycle.HiltViewModel

class RankViewModel(
    private val type: Int,
    private val repo: RankRepository
) : BaseViewModel() {

    fun getRankData(type: Int, version: Int? = null) {
    }

}