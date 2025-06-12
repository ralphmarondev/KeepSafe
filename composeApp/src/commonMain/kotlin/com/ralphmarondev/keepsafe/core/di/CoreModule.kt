package com.ralphmarondev.keepsafe.core.di

import com.ralphmarondev.keepsafe.core.data.network.HttpClientFactory
import com.ralphmarondev.keepsafe.core.theme.ThemeState
import com.ralphmarondev.keepsafe.core.theme.ThemeStateImpl
import org.koin.dsl.module

val coreModule = module {
    single<ThemeState> { ThemeStateImpl(get()) }
    single { HttpClientFactory.create(get()) }
}