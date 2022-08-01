package com.qxy.lib.base.ext

import android.content.Context
import android.widget.Toast

fun Context.toast(msg: String, time: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, msg, time).show()