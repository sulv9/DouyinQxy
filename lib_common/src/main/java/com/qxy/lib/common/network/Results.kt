package com.qxy.lib.common.network

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.qxy.lib.base.base.network.Results
import com.qxy.lib.base.base.network.whenFailure
import com.qxy.lib.base.base.network.whenLoading
import com.qxy.lib.base.base.network.whenSuccess
import com.qxy.lib.base.ext.toast
import com.qxy.lib.common.R

inline fun <T> Results<T>.processView(
    loadingView: View,
    errorView: View,
    successView: View,
    showData: (value: T) -> Unit
) {
    var mLastResult: Results<T> = Results.Loading
    whenLoading {
        loadingView.isVisible = true
        errorView.isVisible = false
        successView.isVisible = false
        loadingView.findViewById<CircularProgressIndicator>(
            com.qxy.lib.common.R.id.common_cpi_loading
        ).isIndeterminate = true
        mLastResult = Results.Loading
    }
    whenFailure {
        val errorMsg = (if (mLastResult is Results.Failure
            && (mLastResult as Results.Failure).errors.level < it.level
        ) (mLastResult as Results.Failure).errors.message
        else it.message) ?: "Error"
        // 显示错误信息
        loadingView.isVisible = false
        if (mLastResult is Results.Success) {
            toast(errorMsg)
        } else {
            errorView.isVisible = true
            errorView.findViewById<TextView>(
                R.id.common_tv_load_fail
            ).text = errorMsg
        }
        mLastResult = Results.Failure(it)
    }
    whenSuccess {
        if (mLastResult is Results.Failure) {
            errorView.isVisible = false
            toast((mLastResult as Results.Failure).errors.message ?: "Error")
        }
        // 展示数据
        loadingView.isVisible = false
        successView.isVisible = true
        showData(it)
        // 更新mLastState
        mLastResult = Results.Success(it)
    }
}