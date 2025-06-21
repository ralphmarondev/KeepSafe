package com.ralphmarondev.keepsafe.settings.di

import com.ralphmarondev.keepsafe.settings.data.repository.SettingRepositoryImpl
import com.ralphmarondev.keepsafe.settings.domain.repository.SettingRepository
import com.ralphmarondev.keepsafe.settings.domain.usecase.LogoutUseCase
import com.ralphmarondev.keepsafe.settings.domain.usecase.SyncWithFirebaseUseCase
import com.ralphmarondev.keepsafe.settings.presentation.overview.SettingViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val settingModule = module {
    singleOf(::SettingRepositoryImpl).bind<SettingRepository>()

    factoryOf(::SyncWithFirebaseUseCase)
    factoryOf(::LogoutUseCase)

    factoryOf(::SettingViewModel)
}