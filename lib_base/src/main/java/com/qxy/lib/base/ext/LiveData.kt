package com.qxy.lib.base.ext

open class SingleLiveEvent<out T>(private val value: T) {

    private var mHasBeenHandled = false

    fun getValueIfNotHandled(): T? {
        return if (mHasBeenHandled) {
            null
        } else {
            mHasBeenHandled = true
            value
        }
    }

    fun peekValue() = value

}