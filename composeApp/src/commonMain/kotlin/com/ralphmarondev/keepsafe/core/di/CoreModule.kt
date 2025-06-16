package com.ralphmarondev.keepsafe.core.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ralphmarondev.keepsafe.core.data.local.database.AppDatabase
import com.ralphmarondev.keepsafe.core.data.local.database.DatabaseFactory
import com.ralphmarondev.keepsafe.core.data.network.HttpClientFactory
import com.ralphmarondev.keepsafe.core.data.network.firebase.auth.LoginApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.auth.RegisterApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.GetDetailsApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.GetMembersApiService
import com.ralphmarondev.keepsafe.core.theme.ThemeState
import com.ralphmarondev.keepsafe.core.theme.ThemeStateImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreModule = module {
    single<ThemeState> { ThemeStateImpl(get()) }
    single { HttpClientFactory.create(get()) }

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<AppDatabase>().userDao }

    // firebase
    singleOf(::LoginApiService)
    singleOf(::RegisterApiService)
    singleOf(::GetMembersApiService)
    singleOf(::GetDetailsApiService)
}