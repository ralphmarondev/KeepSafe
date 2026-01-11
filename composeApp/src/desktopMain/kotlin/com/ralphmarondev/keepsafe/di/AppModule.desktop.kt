package com.ralphmarondev.keepsafe.di

import com.ralphmarondev.keepsafe.core.data.local.database.DatabaseFactory
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual val appModule: Module = module {
    single {
        AppPreferences.create {
            val userHome = System.getProperty("user.home")
            val prefsDir = File(userHome, ".keepsafe")
            prefsDir.mkdirs()
            File(prefsDir, AppPreferences.DATA_STORE_FILE_NAME).absolutePath
        }
    }
    single<HttpClientEngine> { OkHttp.create() }
    single { DatabaseFactory() }
}