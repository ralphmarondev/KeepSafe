package com.ralphmarondev.keepsafe.family_list.di

import com.ralphmarondev.keepsafe.family_list.data.network.FamilyListApiService
import com.ralphmarondev.keepsafe.family_list.presentation.FamilyListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val familyListModule = module {
    singleOf(::FamilyListApiService)
    factoryOf(::FamilyListViewModel)
}