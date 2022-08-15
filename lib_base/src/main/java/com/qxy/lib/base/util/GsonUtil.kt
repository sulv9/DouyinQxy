package com.qxy.lib.base.util

import com.google.gson.Gson

object GsonUtil {
    val INSTANCE = Gson()
}

/**
 * 将任意对象转换为json字符串
 */
fun <T> T.toJson(): String = GsonUtil.INSTANCE.toJson(this)

/**
 * 将json字符串转换为指定类型的对象
 */
inline fun <reified T> String.fromJson(): T? = GsonUtil.INSTANCE.fromJson(this, T::class.java)