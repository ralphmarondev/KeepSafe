package com.ralphmarondev.keepsafe

import android.app.Application
import com.ralphmarondev.keepsafe.di.appModule
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
        startKoin {
            androidContext(this@MyApp)
            modules(appModule)
        }
    }
}