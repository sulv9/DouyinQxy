package com.qxy.module.rank.ui

import com.qxy.lib.base.base.viewmodel.BaseViewModel
import com.qxy.module.rank.data.model.RankItem
import com.qxy.module.rank.data.repo.RankRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class RankViewModel @Inject constructor(
    private val type: Int,
    private val repo: RankRepository
) : BaseViewModel() {

}