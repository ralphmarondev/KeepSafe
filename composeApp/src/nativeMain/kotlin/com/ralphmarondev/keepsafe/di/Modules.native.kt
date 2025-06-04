package com.ralphmarondev.keepsafe.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ralphmarondev.keepsafe.core.data.local.createDataStore
import com.ralphmarondev.keepsafe.core.dependencies.DbClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module = module {
    singleOf(::DbClient)
    single<DataStore<Preferences>> { createDataStore() }
}