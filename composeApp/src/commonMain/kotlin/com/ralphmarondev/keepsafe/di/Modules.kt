package com.ralphmarondev.keepsafe.di

import com.ralphmarondev.keepsafe.AppSettingsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

expect val platformModule: Module

val appModule = module {
    factoryOf(::AppSettingsViewModel)
}