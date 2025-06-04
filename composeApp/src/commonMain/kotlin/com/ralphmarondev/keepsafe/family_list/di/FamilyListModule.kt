package com.ralphmarondev.keepsafe.family_list.di

import com.ralphmarondev.keepsafe.family_list.presentation.FamilyListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val familyListModule = module {
    factoryOf(::FamilyListViewModel)
}