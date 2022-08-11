package com.qxy.lib.base.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel(), IViewModel {
    /**
     * 在viewModel作用域中收集流数据
     */
    protected inline fun <T> Flow<T>.launchCollect(crossinline block: suspend (data: T) -> Unit) {
        viewModelScope.launch {
            collect { block.invoke(it) }
        }
    }
}