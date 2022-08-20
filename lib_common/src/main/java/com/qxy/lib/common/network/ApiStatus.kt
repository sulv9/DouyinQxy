package com.qxy.lib.common.network

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName
import kotlin.jvm.Throws

/**
 * api请求状态码封装
 */
open class ApiStatus(
    @SerializedName("error_code")
    @Ignore
    val errorCode: Int = -1,
    @SerializedName("description")
    @Ignore
    val errorMsg: String = "",
) {
    val isSuccess: Boolean get() = errorCode == 0
}