package com.ralphmarondev.keepsafe.core.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ralphmarondev.keepsafe.core.data.local.database.AppDatabase
import com.ralphmarondev.keepsafe.core.data.local.database.DatabaseFactory
import com.ralphmarondev.keepsafe.core.data.network.HttpClientFactory
import com.ralphmarondev.keepsafe.core.data.network.firebase.auth.LoginApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.auth.RegisterApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.AddMemberApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.DeleteMemberApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.GetDetailsApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.GetMembersApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.UpdateMemberApiService
import com.ralphmarondev.keepsafe.core.data.repository.PreferencesRepositoryImpl
import com.ralphmarondev.keepsafe.core.domain.repository.PreferencesRepository
import com.ralphmarondev.keepsafe.core.domain.usecase.PreferencesUseCase
import com.ralphmarondev.keepsafe.core.theme.ThemeState
import com.ralphmarondev.keepsafe.core.theme.ThemeStateImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
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

    // preferences
    singleOf(::PreferencesRepositoryImpl).bind<PreferencesRepository>()
    factoryOf(::PreferencesUseCase)

    // firebase
    singleOf(::LoginApiService)
    singleOf(::RegisterApiService)
    singleOf(::GetMembersApiService)
    singleOf(::GetDetailsApiService)
    singleOf(::AddMemberApiService)
    singleOf(::UpdateMemberApiService)
    singleOf(::DeleteMemberApiService)
}