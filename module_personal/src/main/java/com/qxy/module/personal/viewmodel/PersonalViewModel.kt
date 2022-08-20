package com.qxy.module.personal.viewmodel

import com.qxy.lib.base.base.viewmodel.BaseViewModel
import com.qxy.module.personal.repo.PersonalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor(
    private val repo: PersonalRepository
) : BaseViewModel() {

}