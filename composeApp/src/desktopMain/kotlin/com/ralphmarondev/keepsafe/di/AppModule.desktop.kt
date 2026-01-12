package com.ralphmarondev.keepsafe.di

import com.ralphmarondev.keepsafe.core.data.local.database.DatabaseFactory
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.data.network.HttpClientFactory
import com.ralphmarondev.keepsafe.core.data.network.firebase.FirebaseAuthentication
import com.ralphmarondev.keepsafe.core.data.network.firebase.FirebaseService
import com.ralphmarondev.keepsafe.features.auth.data.repository.AuthRepositoryImpl
import com.ralphmarondev.keepsafe.features.auth.domain.repository.AuthRepository
import com.ralphmarondev.keepsafe.features.download.data.repository.DownloadRepositoryImpl
import com.ralphmarondev.keepsafe.features.download.domain.repository.DownloadRepository
import com.ralphmarondev.keepsafe.features.members.data.repository.MemberRepositoryImpl
import com.ralphmarondev.keepsafe.features.members.domain.repository.MemberRepository
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.io.File

actual val appModule: Module = module {
    single {
        AppPreferences.create {
            val userHome = System.getProperty("user.home")
            val prefsDir = File(userHome, ".keepsafe")
            prefsDir.mkdirs()
            File(prefsDir, AppPreferences.DATA_STORE_FILE_NAME).absolutePath
        }
    }
    single { DatabaseFactory() }
    single<HttpClientEngine> { OkHttp.create() }
    single { HttpClientFactory.create(get()) }
    single { FirebaseAuthentication(get()) }
    single { FirebaseService(get()) }

    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    singleOf(::DownloadRepositoryImpl).bind<DownloadRepository>()
    singleOf(::MemberRepositoryImpl).bind<MemberRepository>()
}