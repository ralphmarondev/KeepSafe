package com.ralphmarondev.keepsafe.di

import com.ralphmarondev.keepsafe.auth.di.authModule
import com.ralphmarondev.keepsafe.core.di.coreModule
import com.ralphmarondev.keepsafe.family_list.di.familyListModule
import com.ralphmarondev.keepsafe.home.di.homeModule
import com.ralphmarondev.keepsafe.new_family_member.di.newFamilyMemberModule
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
            familyListModule,
            newFamilyMemberModule
        )
    }
}