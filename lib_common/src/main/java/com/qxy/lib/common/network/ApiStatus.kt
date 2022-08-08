package com.qxy.lib.common.network

import com.google.gson.annotations.SerializedName
import kotlin.jvm.Throws

/**
 * api请求状态码封装
 */
open class ApiStatus(
    @SerializedName("error_code")
    val errorCode: Int = -1,
    @SerializedName("description")
    val errorMsg: String = "",
) {
    val isSuccess: Boolean get() = errorCode == 0

    @Throws(ApiException::class)
    fun throwApiExceptionWhenFail() {
        if (!isSuccess) throw ApiException(errorCode, errorMsg)
    }
}