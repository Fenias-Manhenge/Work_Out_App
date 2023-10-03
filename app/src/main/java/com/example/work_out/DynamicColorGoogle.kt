package com.example.work_out

import android.app.Application
import com.google.android.material.color.DynamicColors

class DynamicColorGoogle: Application(){
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}