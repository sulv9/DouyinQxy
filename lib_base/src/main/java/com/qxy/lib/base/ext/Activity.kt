package com.qxy.lib.base.ext

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.DisplayMetrics
import android.view.View
import androidx.annotation.IdRes
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette

/**
 * 参考guolin的博客，自适应设置状态栏颜色
 * blog: https://blog.csdn.net/guolin_blog/article/details/125234545
 */
fun Activity.setStatusBarAuto(bitmap: Bitmap) {
    val colorCount = 5
    val left = 0
    val top = 0
    val right = getScreenWidth()
    val bottom = getStatusBarHeight()

    Palette
        .from(bitmap)
        .maximumColorCount(colorCount)
        .setRegion(left, top, right, bottom)
        .generate {
            it?.let { palette ->
                var mostPopularSwatch: Palette.Swatch? = null
                for (swatch in palette.swatches) {
                    if (mostPopularSwatch == null
                        || swatch.population > mostPopularSwatch.population
                    ) {
                        mostPopularSwatch = swatch
                    }
                }
                mostPopularSwatch?.let { swatch ->
                    val luminance = ColorUtils.calculateLuminance(swatch.rgb)
                    // If the luminance value is lower than 0.5, we consider it as dark.
                    if (luminance < 0.5) {
                        setDarkStatusBar()
                    } else {
                        setLightStatusBar()
                    }
                }
            }
        }
}

fun Activity.setStatusBarAuto(@IdRes imageResource: Int) {
    val bitmap = BitmapFactory.decodeResource(resources, imageResource)
    setStatusBarAuto(bitmap)
}

/**
 * 设置浅色状态栏，图标变为黑色
 */
fun Activity.setLightStatusBar() {
    val flags = window.decorView.systemUiVisibility
    window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

/**
 * 设置深色状态栏，图标为白色
 */
fun Activity.setDarkStatusBar() {
    val flags = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    window.decorView.systemUiVisibility = flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

/**
 * 获取屏幕宽度
 */
fun Activity.getScreenWidth(): Int {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}

/**
 * 获取状态栏高度
 */
fun Activity.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}
