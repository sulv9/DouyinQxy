package com.qxy.module.rank.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qxy.lib.base.base.network.Errors
import com.qxy.lib.base.base.viewmodel.BaseViewModel
import com.qxy.lib.base.ext.AbortFlowWrapper
import com.qxy.module.rank.data.model.RankItem
import com.qxy.module.rank.data.repo.RankRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class RankViewModel(
    type: Int,
    private val repo: RankRepository
) : BaseViewModel() {

    init {
        getRankData(type)
    }

    private val _rankData = MutableLiveData<RankViewState>()
    val rankData get() = _rankData

    fun getRankData(type: Int, version: Int? = null) {
        repo.getRankFlow(type, version).launchCollect { _rankData.value = it }
    }

}