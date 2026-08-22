package com.ralphmarondev.keepsafe.feature.auth.di

import com.ralphmarondev.keepsafe.feature.auth.presentation.login.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    viewModelOf(::LoginViewModel)
}