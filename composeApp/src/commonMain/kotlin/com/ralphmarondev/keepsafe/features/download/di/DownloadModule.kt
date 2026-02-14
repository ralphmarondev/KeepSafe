package com.ralphmarondev.keepsafe.features.download.di

import com.ralphmarondev.keepsafe.features.download.data.repository.DownloadRepositoryImpl
import com.ralphmarondev.keepsafe.features.download.domain.repository.DownloadRepository
import org.koin.dsl.module

val downloadModule = module {
    single<DownloadRepository> { DownloadRepositoryImpl(get()) }
}