package com.pan.common.util

import android.util.Log

object SLog {
    var isDEBUG = false
    var TAG = "SLog"

    fun v(message: String) {
        v(TAG, message)
    }

    fun v(tag: String, message: String) {
        if (isDEBUG) {
            Log.v(tag, message)
        }
    }

    fun d(message: String) {
        d(TAG, message)
    }

    fun d(tag: String, message: String) {
        if (isDEBUG) {
            Log.d(tag, message)
        }
    }

    fun i(message: String) {
        i(TAG, message)
    }

    fun i(tag: String, message: String) {
        if (isDEBUG) {
            Log.i(tag, message)
        }
    }

    fun w(message: String) {
        w(TAG, message)
    }

    fun w(tag: String, message: String) {
        if (isDEBUG) {
            Log.w(tag, message)
        }
    }

    fun e(message: String) {
        e(TAG, message)
    }

    fun e(tag: String, message: String) {
        if (isDEBUG) {
            Log.e(tag, message)
        }
    }

    fun log(message: String, startTime: Long) {
        val time = System.currentTimeMillis() - startTime
        d(message + time + "毫秒")
    }
}