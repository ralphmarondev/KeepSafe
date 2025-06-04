package com.ralphmarondev.keepsafe.home.di

import com.ralphmarondev.keepsafe.home.presentation.HomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val homeModule = module {
    factoryOf(::HomeViewModel)
}