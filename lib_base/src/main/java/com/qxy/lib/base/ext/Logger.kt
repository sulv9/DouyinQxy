package com.qxy.lib.base.ext

import timber.log.Timber

inline fun log(msg: () -> String) = Timber.e(msg.invoke())