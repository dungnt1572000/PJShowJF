package com.example.lastjfproject

import android.app.Application
import com.example.lastjfproject.storageData.DatalocalSharePrefManager
import com.example.lastjfproject.storageData.UserSharePreference

class myApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        DatalocalSharePrefManager.init(applicationContext)
    }
}