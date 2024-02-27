package com.example.work_out

import android.app.Application
import com.example.work_out.room.DateTimeDataBase
import com.google.android.material.color.DynamicColors

class ApplicationWorkOut: Application(){

    val dataBase by lazy { DateTimeDataBase.getInstance(this) }

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}