package com.qxy.lib.base.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object GsonUtil {
    val INSTANCE = Gson()
}

/**
 * 将任意对象转换为json字符串
 */
fun <T> T.toJson(): String = GsonUtil.INSTANCE.toJson(this)
inline fun <reified T> List<T>.toJsonList(): String = GsonUtil.INSTANCE.toJson(this,
    object : TypeToken<List<T>>(){}.type)

/**
 * 将json字符串转换为指定类型的对象
 */
inline fun <reified T> String.fromJson(): T = GsonUtil.INSTANCE.fromJson(this, T::class.java)
inline fun <reified T> String.fromJsonList(): List<T>? = GsonUtil.INSTANCE.fromJson(this,
    object : TypeToken<List<T>>(){}.type)