package com.ralphmarondev.keepsafe.di

import org.koin.dsl.module

val appModule = module {
    includes(sharedModule)
}