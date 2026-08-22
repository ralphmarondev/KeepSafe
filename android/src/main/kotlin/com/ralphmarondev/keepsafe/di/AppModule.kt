package com.ralphmarondev.keepsafe.di

import com.ralphmarondev.keepsafe.feature.auth.di.authModule
import org.koin.dsl.module

val appModule = module {
    includes(sharedModule)
    includes(authModule)
}