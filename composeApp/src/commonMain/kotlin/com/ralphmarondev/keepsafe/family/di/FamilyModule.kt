package com.ralphmarondev.keepsafe.family.di

import com.ralphmarondev.keepsafe.family.data.repository.FamilyRepositoryImpl
import com.ralphmarondev.keepsafe.family.domain.repository.FamilyRepository
import com.ralphmarondev.keepsafe.family.domain.usecase.AddNewFamilyMemberUseCase
import com.ralphmarondev.keepsafe.family.domain.usecase.GetDetailsUseCase
import com.ralphmarondev.keepsafe.family.domain.usecase.GetFamilyMembersUseCase
import com.ralphmarondev.keepsafe.family.presentation.member_detail.FamilyMemberDetailViewModel
import com.ralphmarondev.keepsafe.family.presentation.member_list.FamilyMemberListViewModel
import com.ralphmarondev.keepsafe.family.presentation.new_member.NewFamilyMemberViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val familyModule = module {
    singleOf(::FamilyRepositoryImpl).bind<FamilyRepository>()

    factoryOf(::AddNewFamilyMemberUseCase)
    factoryOf(::GetFamilyMembersUseCase)
    factoryOf(::GetDetailsUseCase)

    factoryOf(::FamilyMemberListViewModel)
    factoryOf(::NewFamilyMemberViewModel)
    factoryOf(::FamilyMemberDetailViewModel)
}