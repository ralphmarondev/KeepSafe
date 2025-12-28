package com.ralphmarondev.keepsafe.features.download.di

import com.ralphmarondev.keepsafe.features.download.data.repository.DownloadRepositoryImpl
import com.ralphmarondev.keepsafe.features.download.domain.repository.DownloadRepository
import com.ralphmarondev.keepsafe.features.download.presentation.DownloadViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val downloadModule = module {
    singleOf(::DownloadRepositoryImpl).bind<DownloadRepository>()
    factoryOf(::DownloadViewModel)
}