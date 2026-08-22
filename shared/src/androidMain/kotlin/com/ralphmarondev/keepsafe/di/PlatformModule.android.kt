package com.ralphmarondev.keepsafe.di

import android.content.Context
import com.ralphmarondev.keepsafe.data.local.preference.AppPreferences
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        AppPreferences.create {
            get<Context>().filesDir.resolve(
                relative = AppPreferences.DATASTORE_FILENAME
            ).absolutePath
        }
    }
}