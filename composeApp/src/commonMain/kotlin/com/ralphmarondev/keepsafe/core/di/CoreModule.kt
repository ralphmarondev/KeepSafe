package com.ralphmarondev.keepsafe.core.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ralphmarondev.keepsafe.core.data.local.database.AppDatabase
import com.ralphmarondev.keepsafe.core.data.local.database.DatabaseFactory
import com.ralphmarondev.keepsafe.core.data.network.HttpClientFactory
import org.koin.dsl.module

val coreModule = module {
    single { HttpClientFactory.create(get()) }
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<AppDatabase>().userDao }
}