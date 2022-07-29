package com.qxy.lib.base

import android.app.Application
import android.content.Context

open class BaseApp : Application() {

    companion object {
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}