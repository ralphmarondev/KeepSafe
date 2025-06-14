package com.ralphmarondev.keepsafe.settings.di

import com.ralphmarondev.keepsafe.settings.presentation.overview.SettingViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val settingModule = module {
    factoryOf(::SettingViewModel)
}