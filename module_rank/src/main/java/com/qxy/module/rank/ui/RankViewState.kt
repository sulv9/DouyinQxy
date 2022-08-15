package com.qxy.module.rank.ui

import com.qxy.lib.base.base.network.Errors
import com.qxy.module.rank.data.model.RankItem

data class RankViewState(
    val isLoading: Boolean = false,
    val apiError: Errors.ApiError? = null,
    val networkError: Errors.NetworkError? = null,
    val emptyError: Errors.EmptyResultError? = null,
    val unknownError: Errors.UnknownError? = null,
    val rankList: List<RankItem>? = null,
)