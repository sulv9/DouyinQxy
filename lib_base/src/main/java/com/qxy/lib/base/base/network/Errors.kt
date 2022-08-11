package com.qxy.lib.base.base.network

import java.io.IOException

sealed class Errors(message: String) : RuntimeException(message) {
    data class ApiError(
        val errorCode: Int,
        val errorMsg: String
    ) : Errors("Api请求异常 -> 错误码: $errorCode  错误信息:$errorMsg")

    object NetworkError : Errors("网络请求异常")

    object EmptyResultError : Errors("结果为空")
}

/**
 * 处理错误
 */
inline fun <T> processErrors(block: () -> T): T {
    return try {
        block.invoke()
    } catch (e : IOException) {
        throw Errors.NetworkError
    }
}