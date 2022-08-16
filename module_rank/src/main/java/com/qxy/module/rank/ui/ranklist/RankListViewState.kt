package com.qxy.module.rank.ui.ranklist

import com.qxy.lib.base.base.network.Results
import com.qxy.module.rank.data.model.RankItem

data class RankListViewState(
    val type: Int,
    val version: Int?,
    val result: Results<List<RankItem>>,
) {
    companion object {
        fun initial() = RankListViewState(
            type = -1,
            version = null,
            result = Results.loading(),
        )
    }
}