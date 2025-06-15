package com.ralphmarondev.keepsafe.family.di

import com.ralphmarondev.keepsafe.family.data.network.FamilyApiService
import com.ralphmarondev.keepsafe.family.presentation.member_list.FamilyMemberListViewModel
import com.ralphmarondev.keepsafe.family.presentation.new_member.NewFamilyMemberViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val familyModule = module {
    singleOf(::FamilyApiService)

    factoryOf(::FamilyMemberListViewModel)
    factoryOf(::NewFamilyMemberViewModel)
}