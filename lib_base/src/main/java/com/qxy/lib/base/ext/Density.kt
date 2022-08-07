package com.qxy.lib.base.ext

import android.content.res.Resources.getSystem

/**
 * dp To px
 */
val Int.dp get() = (this * getSystem().displayMetrics.density).toInt()
val Float.dp get() = this * getSystem().displayMetrics.density

/**
 * sp To px
 */
val Int.sp get() = (this.dp)