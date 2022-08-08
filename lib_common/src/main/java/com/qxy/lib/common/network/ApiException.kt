package com.qxy.lib.common.network

/**
 * api请求错误的封装
 */
class ApiException(
    errorCode: Int,
    errorMsg: String,
) : RuntimeException("Api Exception -> errorCode: $errorCode  errorMsg:$errorMsg")