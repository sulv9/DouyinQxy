package com.qxy.module.rank.ui.ranklist

import androidx.lifecycle.MutableLiveData
import com.qxy.lib.base.base.network.Errors
import com.qxy.lib.base.base.viewmodel.BaseViewModel
import com.qxy.module.rank.data.repo.RankRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RankListViewModel @Inject constructor(
    private val repo: RankRepository,
) : BaseViewModel() {

    private var isInitialized = false

    fun initialize(type: Int) {
        if (isInitialized) return
        isInitialized = true
        getRankData(type)
    }

    private val _rankData = MutableLiveData<RankListViewState>()
    val rankData get() = _rankData

    /**
     * 在ViewModel中对Flow进行处理
     */
    fun getRankData(type: Int, version: Int? = null) {
        val state = RankListViewState(isLoading = true)
        repo.getRankFlow(type, version)
            .map { state.copy(rankList = it, isLoading = false) }
            .flowOn(Dispatchers.IO)
            .onStart { emit(state) }
            .catch {
                when (it) {
                    is Errors.ApiError -> emit(state.copy(apiError = it, isLoading = false))
                    is Errors.NetworkError -> emit(state.copy(networkError = it, isLoading = false))
                    is Errors.EmptyResultError -> emit(state.copy(emptyError = it, isLoading = false))
                    else -> emit(state.copy(unknownError = Errors.UnknownError(it), isLoading = false))
                }
            }.launchCollect { _rankData.value = it }
    }

}