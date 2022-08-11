package com.qxy.lib.common.network

import com.google.gson.annotations.SerializedName
import com.qxy.lib.base.base.network.Errors
import com.qxy.lib.base.base.network.processErrors
import java.io.Serializable

/**
 * api网络请求响应结果的封装
 */
class ApiResponse<T>(
    @SerializedName("data")
    val data: T? = null,
) : Serializable

/**
 * 处理api响应
 */
inline fun <T : ApiStatus> processApiResponse(request: () -> ApiResponse<T>): T {
    return processErrors {
        val apiResponse = request()
        val data = apiResponse.data ?: throw Errors.EmptyResultError
        if (data.isSuccess) {
            data
        } else {
            throw Errors.ApiError(data.errorCode, data.errorMsg)
        }
    }
}