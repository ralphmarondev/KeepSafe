package com.ralphmarondev.keepsafe.di

import com.ralphmarondev.keepsafe.core.di.coreModule
import com.ralphmarondev.keepsafe.features.auth.di.authModule
import com.ralphmarondev.keepsafe.features.download.di.downloadModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            modules = listOf(
                appModule,
                coreModule,
                authModule,
                downloadModule
            )
        )
    }
}