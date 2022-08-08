package com.qxy.lib.common.network

import com.qxy.lib.common.BuildConfig

const val DEBUG_URL = "https://open.douyin.com/"
const val RELEASE_URL = "https://open.douyin.com/"

val base_url get() = if (BuildConfig.DEBUG) DEBUG_URL else RELEASE_URL