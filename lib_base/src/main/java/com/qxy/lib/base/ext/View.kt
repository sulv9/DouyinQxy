package com.qxy.lib.base.ext

import android.view.View
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

val View.trueHeight get() = height + marginTop + marginBottom + paddingTop + paddingBottom

val View.trueWidth get() = width + marginLeft + marginRight + paddingStart + paddingEnd