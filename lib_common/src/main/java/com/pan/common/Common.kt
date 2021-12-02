package com.pan.common

import android.content.Context

object Common {
    fun filePath(context: Context):String?{
       return context.getExternalFilesDir(null)?.path
    }
}