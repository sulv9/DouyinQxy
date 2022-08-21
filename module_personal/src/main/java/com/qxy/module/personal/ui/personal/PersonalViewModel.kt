package com.qxy.module.personal.ui.personal

import com.qxy.lib.base.base.network.Results
import com.qxy.lib.base.base.viewmodel.BaseViewModel
import com.qxy.module.personal.data.model.PersonalInfo
import com.qxy.module.personal.data.repo.PersonalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor(
    private val repo: PersonalRepository
) : BaseViewModel() {

    private val _personal = MutableStateFlow<Results<PersonalInfo>>(Results.Loading)
    val personal = _personal.asStateFlow()

    fun collectPersonalInfo() {
        repo.getPersonalInfo().launchCollect {
            _personal.value = it
        }
    }

    fun collectPersonalVideo(cursor: Int, count: Int) {
        repo.getPersonalVideo(cursor, count).launchCollect {
            // todo
        }
    }
}