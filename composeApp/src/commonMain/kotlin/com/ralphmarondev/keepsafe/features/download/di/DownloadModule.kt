package com.ralphmarondev.keepsafe.features.download.di

import com.ralphmarondev.keepsafe.features.download.presentation.DownloadViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val downloadModule = module {
    viewModelOf(::DownloadViewModel)
}