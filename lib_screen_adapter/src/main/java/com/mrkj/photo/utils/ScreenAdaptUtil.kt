package com.mrkj.photo.utils

import android.app.Activity
import androidx.fragment.app.Fragment

//今日头条屏幕适配
fun Activity.setCustomDensity(){
    val applicationMetrics = this.application.resources.displayMetrics
    var targetDensity:Float = (applicationMetrics.widthPixels/360).toFloat()
    var targetDensityDpi = 160*targetDensity

    applicationMetrics.density = targetDensity
    applicationMetrics.scaledDensity = targetDensity
    applicationMetrics.densityDpi = targetDensityDpi.toInt()

    val activityMetrics = this.resources.displayMetrics
    activityMetrics.density = targetDensity
    activityMetrics.scaledDensity = targetDensity
    activityMetrics.densityDpi = targetDensityDpi.toInt()
}

fun Fragment.setCustomDensity(){
    val applicationMetrics = activity!!.application.resources.displayMetrics
    var targetDensity:Float = (applicationMetrics.widthPixels/360).toFloat()
    var targetDensityDpi = 160*targetDensity

    applicationMetrics.density = targetDensity
    applicationMetrics.scaledDensity = targetDensity
    applicationMetrics.densityDpi = targetDensityDpi.toInt()

    val activityMetrics = this.resources.displayMetrics
    activityMetrics.density = targetDensity
    activityMetrics.scaledDensity = targetDensity
    activityMetrics.densityDpi = targetDensityDpi.toInt()
}