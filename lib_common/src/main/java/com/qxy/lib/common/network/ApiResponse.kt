package com.qxy.lib.common.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import kotlin.jvm.Throws

/**
 * api网络请求响应结果的封装
 */
class ApiResponse<T>(
    @SerializedName("data")
    val data: T? = null,
) : Serializable