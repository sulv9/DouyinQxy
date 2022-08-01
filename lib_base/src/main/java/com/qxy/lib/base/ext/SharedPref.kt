package com.qxy.lib.base.ext

import android.content.Context
import android.content.SharedPreferences

const val DEFAULT_SHARED_PREF_NAME = "default_sp_name"

val Context.sharedPrefDefault: SharedPreferences
    get() = getSharedPreferences(DEFAULT_SHARED_PREF_NAME, Context.MODE_PRIVATE)