package com.ralphmarondev.keepsafe.new_family_member.di

import com.ralphmarondev.keepsafe.new_family_member.presentation.NewFamilyMemberViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val newFamilyMemberModule = module {
    factoryOf(::NewFamilyMemberViewModel)
}