package com.qxy.module.personal.ui.fanfollow.fan

import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.qxy.lib.base.base.network.Results
import com.qxy.lib.base.base.viewmodel.BaseViewModel
import com.qxy.module.personal.data.model.PersonalFanFollowing
import com.qxy.module.personal.data.repo.PersonalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PersonalFanViewModel @Inject constructor(
    private val repo: PersonalRepository
) : BaseViewModel() {
    private var isInitialized = false
    private var _isLoadingMoreFans = false

    private val _fanListData: MutableStateFlow<Results<List<PersonalFanFollowing>>> =
        MutableStateFlow(Results.Loading)
    val fanData get() = _fanListData.asLiveData().distinctUntilChanged()

    fun initialize() {
        if (isInitialized) return
        fetchMoreFanData(cursor = 0, isRefresh = true)
        collectFanData()
        isInitialized = true
    }

    fun fetchMoreFanData(cursor: Long? = null, isRefresh: Boolean) {
        if (_isLoadingMoreFans) return
        _isLoadingMoreFans = true
        viewModelScope.launch {
            val queryCursor = cursor ?: repo.getNewestFanCursor()
            repo.fetchFanData(queryCursor, isRefresh)
            _isLoadingMoreFans = false
        }
    }

    private fun collectFanData() {
        repo.getFanList().launchCollect {
            _fanListData.value = it
        }
    }
}