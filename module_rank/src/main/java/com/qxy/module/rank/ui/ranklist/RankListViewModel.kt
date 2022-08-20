package com.qxy.module.rank.ui.ranklist

import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.qxy.lib.base.base.network.Errors
import com.qxy.lib.base.base.network.Results
import com.qxy.lib.base.base.viewmodel.BaseViewModel
import com.qxy.lib.base.ext.toFormatDate
import com.qxy.module.rank.data.model.RankItem
import com.qxy.module.rank.data.model.RankVersion
import com.qxy.module.rank.data.model.RankVersionItem
import com.qxy.module.rank.data.repo.RankRepository
import com.qxy.module.rank.ext.transformDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankListViewModel @Inject constructor(
    private val repo: RankRepository,
) : BaseViewModel() {

    private var isInitialized = false
    private var type = -1

    private val _rankData = MutableStateFlow(RankListViewState.initial())
    val rankData get() = _rankData.asLiveData().distinctUntilChanged()

    private val _versionListData: MutableStateFlow<Results<List<RankVersion>>> =
        MutableStateFlow(Results.Loading)
    val versionListData get() = _versionListData.asLiveData().distinctUntilChanged()

    private var _isLoadingMoreVersion = false

    fun initialize(type: Int) {
        if (isInitialized) {
            // 未请求成功返回该界面时，自动重新请求网络数据
            if (isRemoteRequestFail()) fetchRankDataFromRemote()
            return
        }
        isInitialized = true
        this.type = type
        fetchRankData()
        fetchMoreVersionData(cursor = 0, isRefresh = true)
        collectRankVersionData()
    }

    /**
     * 在ViewModel中对Flow进行处理
     */
    fun fetchRankData(version: RankVersionItem? = null) {
        val state = _rankData.value
        repo.getRankListFlow(type, version?.version)
            .map { state.copy(result = it, rankInfo = getRankInfo(it, version)) }
            .onStart { emit(RankListViewState.initial()) }
            .launchCollect { _rankData.value = it }
    }

    /**
     * 只从网络请求数据
     */
    private fun fetchRankDataFromRemote(version: RankVersionItem? = null) {
        val state = _rankData.value
        repo.getRankListFlowFromRemote(type, version?.version)
            .map { state.copy(result = it, rankInfo = getRankInfo(it, version)) }
            .flowOn(Dispatchers.IO)
            .onStart { emit(RankListViewState.initial()) }
            .launchCollect { _rankData.value = it }
    }

    /**
     * 获取当前榜单显示信息
     */
    private fun getRankInfo(results: Results<List<RankItem>>, version: RankVersionItem?) =
        if (results is Results.Success) {
            val rankItem = results.value.first()
            val activeTime = rankItem.activeTime?.split("-")
            if (version == null) "本周榜 | 更新于${activeTime?.get(1)}月${activeTime?.get(2)}日 12:00"
            else "第${rankItem.version}期 ${version.startTime.transformDate} - " +
                    version.endTime.transformDate
        } else ""

    /**
     * 是否网络数据请求失败
     */
    private fun isRemoteRequestFail() = _rankData.value.result is Results.Failure
            && (_rankData.value.result as Results.Failure).errors is Errors.NetworkError

    /**
     * 加载更多版本数据
     */
    fun fetchMoreVersionData(cursor: Int? = null, isRefresh: Boolean) {
        if (_isLoadingMoreVersion) return
        _isLoadingMoreVersion = true
        viewModelScope.launch {
            val queryCursor = cursor ?: repo.getNewestRankVersionCursor(type)
            repo.loadRankVersion(queryCursor, type, isRefresh)
            _isLoadingMoreVersion = false
        }
    }

    private fun collectRankVersionData() {
        repo.getRankVersionList(type).launchCollect {
            _versionListData.value = it
        }
    }

}