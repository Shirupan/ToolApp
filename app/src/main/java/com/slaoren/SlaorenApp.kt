package com.slaoren

import android.app.Application
import com.slaoren.common.util.SLog

class SlaorenApp: Application() {
    override fun onCreate() {
        super.onCreate()
        SLog.isDEBUG = true
    }
}