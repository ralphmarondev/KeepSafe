package com.ralphmarondev.keepsafe.auth.di

import com.ralphmarondev.keepsafe.auth.data.network.AuthService
import com.ralphmarondev.keepsafe.auth.data.repository.AuthRepositoryImpl
import com.ralphmarondev.keepsafe.auth.domain.repository.AuthRepository
import com.ralphmarondev.keepsafe.auth.domain.usecase.LoginUseCase
import com.ralphmarondev.keepsafe.auth.presentation.login.LoginViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    singleOf(::AuthService)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()

    factoryOf(::LoginUseCase)

    factoryOf(::LoginViewModel) // NOTE: user factoryOf instead of viewmodelOf for platform safe
}