package com.ralphmarondev.keepsafe.di

import com.ralphmarondev.keepsafe.core.di.coreModule
import com.ralphmarondev.keepsafe.features.auth.di.authModule
import com.ralphmarondev.keepsafe.features.download.di.downloadModule
import com.ralphmarondev.keepsafe.features.members.di.membersModule
import org.koin.dsl.module

val appModule = module {
    includes(coreModule)
    includes(authModule)
    includes(downloadModule)
    includes(membersModule)
}