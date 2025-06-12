package com.ralphmarondev.keepsafe.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ralphmarondev.keepsafe.core.data.local.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<DataStore<Preferences>> {
        createDataStore(get<Context>())
    }
}