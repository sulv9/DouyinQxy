package com.qxy.lib.base.util

import java.lang.reflect.ParameterizedType

object GenericUtil {
    /**
     * 得到父类中的泛型Class对象
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <K : E, reified E> getGenericFromSuper(javaClass: Class<*>): Class<K> {
        // 得到父类泛型
        val genericSuperClass = javaClass.genericSuperclass
        if (genericSuperClass is ParameterizedType) {
            for (type in genericSuperClass.actualTypeArguments) {
                if (type is Class<*> && E::class.java.isAssignableFrom(type)) {
                    return type as Class<K>
                }
                if (type is ParameterizedType) {
                    val rawType = type.rawType
                    if (rawType is Class<*> && E::class.java.isAssignableFrom(rawType)) {
                        return rawType as Class<K>
                    }
                }
            }
        }
        throw RuntimeException("父类泛型为：${genericSuperClass}, 不存在${E::class.simpleName}")
    }
}