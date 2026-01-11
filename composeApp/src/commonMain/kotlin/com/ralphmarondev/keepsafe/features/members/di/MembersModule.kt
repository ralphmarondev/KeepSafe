package com.ralphmarondev.keepsafe.features.members.di

import com.ralphmarondev.keepsafe.features.download.presentation.DownloadViewModel
import com.ralphmarondev.keepsafe.features.members.presentation.member_details.MemberDetailViewModel
import com.ralphmarondev.keepsafe.features.members.presentation.member_list.MemberListViewModel
import com.ralphmarondev.keepsafe.features.members.presentation.new_member.NewMemberViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val membersModule = module {
    viewModelOf(::DownloadViewModel)
    viewModelOf(::MemberListViewModel)
    viewModelOf(::MemberDetailViewModel)
    viewModelOf(::NewMemberViewModel)
}