package com.qxy.lib.base.ext

import java.text.SimpleDateFormat
import java.util.*

const val DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"
const val TIME_PATTERN_NO_HOUR = "yyyy-MM-dd"

const val DAY_MILLIS = 24 * 60 * 60 * 1000

/**
 * 字符串日期转换为时间戳
 */
fun String.toTimeStamp(pattern: String = DEFAULT_TIME_PATTERN) =
    SimpleDateFormat(pattern).parse(this)?.time ?: 0

/**
 * 时间转换为字符串日期
 */
fun Long.toFormatDate(pattern: String = DEFAULT_TIME_PATTERN) =
    SimpleDateFormat(pattern).format(Date(this)) ?: ""

/**
 * 1-7对于周日-周一-...-周六
 */
fun getWeekOfDate(date: Date): Int {
    val calendar = Calendar.getInstance().apply { time = date }
    return calendar.get(Calendar.DAY_OF_WEEK)
}