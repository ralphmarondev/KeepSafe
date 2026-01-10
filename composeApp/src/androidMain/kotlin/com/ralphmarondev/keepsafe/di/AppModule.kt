package com.ralphmarondev.keepsafe.di

import com.ralphmarondev.keepsafe.core.di.coreModule
import com.ralphmarondev.keepsafe.features.auth.di.authModule
import org.koin.dsl.module

val appModule = module {
    includes(coreModule)
    includes(authModule)
}