package com.qxy.lib.base.util

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <reified T> Fragment.args(): ReadWriteProperty<Fragment, T> = bundleDelegate {
    arguments ?: Bundle()
}

inline fun <F, reified T> bundleDelegate(
    crossinline bundleProvider: (F) -> Bundle
): ReadWriteProperty<F, T> =
    object : ReadWriteProperty<F, T> {
        override fun getValue(thisRef: F, property: KProperty<*>) =
            bundleProvider(thisRef).get(property.name) as T

        override fun setValue(thisRef: F, property: KProperty<*>, value: T) =
            bundleProvider(thisRef).putAll(bundleOf(property.name to value))
    }