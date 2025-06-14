package com.ralphmarondev.keepsafe.new_family_member.di

import com.ralphmarondev.keepsafe.new_family_member.data.network.NewFamilyMemberApiService
import com.ralphmarondev.keepsafe.new_family_member.presentation.NewFamilyMemberViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val newFamilyMemberModule = module {
    singleOf(::NewFamilyMemberApiService)
    factoryOf(::NewFamilyMemberViewModel)
}