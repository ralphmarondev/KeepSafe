package com.ralphmarondev.keepsafe.feature.family.di

import com.ralphmarondev.keepsafe.feature.family.presentation.member_detail.MemberDetailViewModel
import com.ralphmarondev.keepsafe.feature.family.presentation.member_list.MemberListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val familyModule = module {
    viewModelOf(::MemberListViewModel)
    viewModelOf(::MemberDetailViewModel)
}