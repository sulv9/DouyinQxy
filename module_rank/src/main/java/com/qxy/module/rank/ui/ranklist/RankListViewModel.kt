package com.qxy.module.rank.ui.ranklist

import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import com.qxy.lib.base.base.network.Errors
import com.qxy.lib.base.base.network.Results
import com.qxy.lib.base.base.viewmodel.BaseViewModel
import com.qxy.module.rank.data.model.RankItem
import com.qxy.module.rank.data.repo.RankRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val MIN_TIME_LOADING = 500L // 加载条显示的最小时间

@HiltViewModel
class RankListViewModel @Inject constructor(
    private val repo: RankRepository,
) : BaseViewModel() {

    private var isInitialized = false

    private val _rankData = MutableStateFlow<Results<List<RankItem>>>(Results.loading())
    val rankData get() = _rankData.asLiveData().distinctUntilChanged()

    private val viewState = MutableStateFlow(RankListViewState.initial())

    fun initialize(type: Int) {
        if (isInitialized) {
            // 未请求成功返回该界面时，自动重新请求网络数据
            if (isRemoteRequestFail()) fetchRankDataFromRemote(type)
            return
        }
        isInitialized = true
        viewState.update { it.copy(type = type) }
        fetchRankData(type)
    }

    fun updateVersion(version: Int?) {
        viewState.update { it.copy(version = version) }
    }

    /**
     * 在ViewModel中对Flow进行处理
     */
    fun fetchRankData(type: Int, version: Int? = null) {
        repo.getRankListFlow(type, version)
            .flowOn(Dispatchers.IO)
            .launchCollect { _rankData.value = it }
    }

    /**
     * 只从网络请求数据
     */
    private fun fetchRankDataFromRemote(type: Int, version: Int? = null) {
        repo.getRankListFlowFromRemote(type, version)
            .flowOn(Dispatchers.IO)
            .onStart { emit(Results.loading()) }
            .launchCollect { _rankData.value = it }
    }

    /**
     * 是否网络数据请求失败
     */
    private fun isRemoteRequestFail() = _rankData.value.state is Results.State.ERROR
            && (_rankData.value.state as Results.State.ERROR).errors is Errors.NetworkError

}