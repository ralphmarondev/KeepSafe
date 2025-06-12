package com.ralphmarondev.keepsafe.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ralphmarondev.keepsafe.core.data.local.createDataStore
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<DataStore<Preferences>> { createDataStore() }
    single<HttpClientEngine> { Darwin.create() }
}