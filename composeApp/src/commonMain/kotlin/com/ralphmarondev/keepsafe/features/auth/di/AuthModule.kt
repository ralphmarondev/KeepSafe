package com.ralphmarondev.keepsafe.features.auth.di

import com.ralphmarondev.keepsafe.features.auth.data.repository.AuthRepositoryImpl
import com.ralphmarondev.keepsafe.features.auth.domain.repository.AuthRepository
import com.ralphmarondev.keepsafe.features.auth.presentation.login.LoginViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get(), get()) }
    factoryOf(::LoginViewModel)
}