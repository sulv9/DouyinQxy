package com.qxy.module.personal.ui.fanfollow.follow

import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.qxy.lib.base.base.network.Results
import com.qxy.lib.base.base.viewmodel.BaseViewModel
import com.qxy.module.personal.data.model.PersonalFanFollowing
import com.qxy.module.personal.data.repo.PersonalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalFollowViewModel @Inject constructor(
    private val repo: PersonalRepository
) : BaseViewModel() {
    private var isInitialized = false
    private var _isLoadingMoreFollowing = false

    private val _followingListData: MutableStateFlow<Results<List<PersonalFanFollowing>>> =
        MutableStateFlow(Results.Loading)
    val followingData get() = _followingListData.asLiveData().distinctUntilChanged()

    fun initialize() {
        if (isInitialized) return
        fetchMoreFollowingData(cursor = 0, isRefresh = true)
        collectFollowingData()
        isInitialized = true
    }

    fun fetchMoreFollowingData(cursor: Long? = null, isRefresh: Boolean) {
        if (_isLoadingMoreFollowing) return
        _isLoadingMoreFollowing = true
        viewModelScope.launch {
            val queryCursor = cursor ?: repo.getNewestFollowingCursor()
            repo.fetchFollowingData(queryCursor, isRefresh)
            _isLoadingMoreFollowing = false
        }
    }

    private fun collectFollowingData() {
        repo.getFollowingList().launchCollect {
            _followingListData.value = it
        }
    }
}