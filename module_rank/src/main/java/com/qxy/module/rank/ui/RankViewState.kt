package com.qxy.module.rank.ui

import com.qxy.module.rank.data.model.RankItem

data class RankViewState(
    val isLoading: Boolean = false,
    val isApiError: Boolean = false,
    val isNetworkError: Boolean = false,
    val isEmptyError: Boolean = false,
    val rankList: List<RankItem>? = null,
)