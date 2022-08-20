package com.qxy.module.rank.ui.ranklist

import com.qxy.lib.base.base.network.Results
import com.qxy.module.rank.data.model.RankItem

data class RankListViewState(
    val result: Results<List<RankItem>>,
    val rankInfo: String,
) {
    companion object {
        fun initial() = RankListViewState(
            result = Results.Loading,
            rankInfo = "",
        )
    }
}