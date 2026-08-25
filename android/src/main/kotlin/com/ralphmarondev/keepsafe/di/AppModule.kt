package com.ralphmarondev.keepsafe.di

import com.ralphmarondev.keepsafe.feature.auth.di.authModule
import com.ralphmarondev.keepsafe.feature.family.di.familyModule
import org.koin.dsl.module

val appModule = module {
    includes(sharedModule)
    includes(authModule)
    includes(familyModule)
}