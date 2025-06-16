package com.ralphmarondev.keepsafe

import android.app.Application
import com.ralphmarondev.keepsafe.core.util.NotificationService
import com.ralphmarondev.keepsafe.core.util.androidNotificationService
import com.ralphmarondev.keepsafe.di.initKoin
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@MyApp)
        }
        val notificationService: NotificationService = get()
        androidNotificationService = notificationService
    }
}