package com.slaoren.common.util

import android.content.Context
import com.google.android.material.internal.ViewUtils.dpToPx

object SizeUtil {

    fun dp2Px(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

    fun px2Dp(context: Context, px: Float): Float {
        return px / context.resources.displayMetrics.density
    }

    fun sp2Px(context: Context, sp: Float): Float {
        return sp * context.resources.displayMetrics.density
    }

    fun px2Sp(context: Context, px: Float): Float {
        return px / context.resources.displayMetrics.density
    }



    fun dp2PxInt(context: Context, dp: Float): Int {
        return (dp2Px(context, dp) + 0.5f).toInt()
    }

    fun px2DpInt(context: Context, px: Float): Int {
        return (px2Dp(context, px) + 0.5f).toInt()
    }
}