package com.qxy.lib.base.ext

import java.text.SimpleDateFormat
import java.util.*

const val DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss"

/**
 * 字符串日期转换为时间戳
 */
fun String.toTimeStamp(pattern: String = DEFAULT_PATTERN) =
    SimpleDateFormat(pattern).parse(this)?.time ?: 0

/**
 * 时间转换为字符串日期
 */
fun Long.toFormatDate(pattern: String = DEFAULT_PATTERN) =
    SimpleDateFormat(pattern).format(Date(this)) ?: ""