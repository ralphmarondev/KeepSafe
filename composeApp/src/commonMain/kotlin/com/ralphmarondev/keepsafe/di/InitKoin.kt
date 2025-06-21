package com.ralphmarondev.keepsafe.di

import com.ralphmarondev.keepsafe.auth.di.authModule
import com.ralphmarondev.keepsafe.core.di.coreModule
import com.ralphmarondev.keepsafe.family.di.familyModule
import com.ralphmarondev.keepsafe.home.di.homeModule
import com.ralphmarondev.keepsafe.reminder.di.reminderModule
import com.ralphmarondev.keepsafe.settings.di.settingModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            platformModule,
            coreModule,
            authModule,
            homeModule,
            familyModule,
            settingModule,
            reminderModule
        )
    }
}