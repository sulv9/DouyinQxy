package com.qxy.lib.base.ext

import android.widget.Toast
import com.qxy.lib.base.BaseApp

fun toast(msg: String, time: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(BaseApp.context, msg, time).show()