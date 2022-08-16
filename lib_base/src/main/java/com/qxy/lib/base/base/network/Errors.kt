package com.qxy.lib.base.base.network

import java.io.IOException

/**
 * @param message 错误信息
 * @param level 错误显示优先级，优先级越小越先展示
 */
sealed class Errors(message: String, val level: Int) : RuntimeException(message) {
    object NetworkError : Errors("网络请求异常", 1)

    data class ApiError(
        val errorCode: Int,
        val errorMsg: String
    ) : Errors("Api请求异常 -> 错误码: $errorCode  错误信息:$errorMsg", 2)

    object EmptyResultError : Errors("结果为空", 3)

    data class UnknownError(val throwable: Throwable) : Errors("未知异常 $throwable", 4)
}

/**
 * 处理错误
 */
inline fun <T> processErrors(block: () -> T): T {
    return try {
        block.invoke()
    } catch (e: IOException) {
        throw Errors.NetworkError
    }
}