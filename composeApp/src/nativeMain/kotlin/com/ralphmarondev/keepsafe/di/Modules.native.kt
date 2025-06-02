package com.ralphmarondev.keepsafe.di

import com.ralphmarondev.keepsafe.core.dependencies.DbClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module = module {
    singleOf(::DbClient)
}