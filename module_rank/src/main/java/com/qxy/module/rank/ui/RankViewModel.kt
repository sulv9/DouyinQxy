package com.qxy.module.rank.ui

import androidx.lifecycle.MutableLiveData
import com.qxy.lib.base.base.viewmodel.BaseViewModel
import com.qxy.lib.base.ext.log
import com.qxy.module.rank.data.repo.RankRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RankViewModel @Inject constructor(
    private val repo: RankRepository,
) : BaseViewModel() {

    private var isInitialized = false

    fun initialize(type: Int) {
        if (isInitialized) return
        log { "init" }
        isInitialized = true
        getRankData(type)
    }

    private val _rankData = MutableLiveData<RankViewState>()
    val rankData get() = _rankData

    fun getRankData(type: Int, version: Int? = null) {
        repo.getRankFlow(type, version).launchCollect {
            log { "collect $it" }
            _rankData.value = it }
    }

}