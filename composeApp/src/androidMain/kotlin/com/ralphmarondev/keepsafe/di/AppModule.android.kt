package com.ralphmarondev.keepsafe.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.ralphmarondev.keepsafe.core.data.local.database.DatabaseFactory
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
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
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val appModule: Module = module {
    single {
        AppPreferences.create {
            get<Context>().filesDir.resolve(
                relative = AppPreferences.DATA_STORE_FILE_NAME
            ).absolutePath
        }
    }
    single { DatabaseFactory(androidApplication()) }
    single<HttpClientEngine> { OkHttp.create() }

    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }
    singleOf(::FirebaseAuthentication)
    singleOf(::FirebaseService)

    // actual implementations: we can create `di` inside each feature too, but this is it for now!
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    singleOf(::DownloadRepositoryImpl).bind<DownloadRepository>()
    singleOf(::MemberRepositoryImpl).bind<MemberRepository>()
}