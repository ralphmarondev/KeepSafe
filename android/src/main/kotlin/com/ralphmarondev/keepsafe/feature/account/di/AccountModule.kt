package com.ralphmarondev.keepsafe.feature.account.di

import com.ralphmarondev.keepsafe.feature.account.presentation.overview.OverviewViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val accountModule = module {
    viewModelOf(::OverviewViewModel)
}