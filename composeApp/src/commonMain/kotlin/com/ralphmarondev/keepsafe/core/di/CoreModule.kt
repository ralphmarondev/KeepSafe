package com.ralphmarondev.keepsafe.core.di

import com.ralphmarondev.keepsafe.core.data.network.HttpClientFactory
import org.koin.dsl.module

val coreModule = module {
    single { HttpClientFactory.create(get()) }
}