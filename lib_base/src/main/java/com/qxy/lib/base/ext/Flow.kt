package com.qxy.lib.base.ext

/**
 * 对流的截断进行封装
 * 当[abort]为true时停止接收流
 */
data class AbortFlowWrapper<T>(
    val data: T,
    val abort: Boolean, // 是否中断流
)