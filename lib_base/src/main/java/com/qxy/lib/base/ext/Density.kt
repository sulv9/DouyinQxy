package com.qxy.lib.base.ext

import android.content.res.Resources.getSystem

/**
 * dp To px
 */
inline val Int.dp get() = (this * getSystem().displayMetrics.density).toInt()
inline val Float.dp get() = this * getSystem().displayMetrics.density

/**
 * px To dp
 */
inline val Int.pxToDp get() = (this / getSystem().displayMetrics.density).toInt()
inline val Float.pxToDp get() = this / getSystem().displayMetrics.density

/**
 * sp To px
 */
inline val Int.sp get() = (this * getSystem().displayMetrics.scaledDensity).toInt()
inline val Float.sp get() = this * getSystem().displayMetrics.scaledDensity

/**
 * px To sp
 */
inline val Int.pxToSp get() = (this / getSystem().displayMetrics.scaledDensity).toInt()
inline val Float.pxToSp get() = this / getSystem().displayMetrics.scaledDensity