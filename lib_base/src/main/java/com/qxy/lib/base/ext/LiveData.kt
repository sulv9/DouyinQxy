package com.qxy.lib.base.ext

open class Event<out T>(private val value: T) {

    private var mHasBeenHandled = false

    fun getValueIfNotHandled(): T? {
        return if (mHasBeenHandled) {
            null
        } else {
            mHasBeenHandled = true
            value
        }
    }

}