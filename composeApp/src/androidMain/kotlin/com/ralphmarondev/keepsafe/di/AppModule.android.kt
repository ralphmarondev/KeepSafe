package com.ralphmarondev.keepsafe.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.ralphmarondev.keepsafe.core.data.local.database.DatabaseFactory
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.data.network.FirebaseAuthManager
import com.ralphmarondev.keepsafe.core.data.network.FirebaseService
import com.ralphmarondev.keepsafe.core.data.network.FirebaseStoreManager
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
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

    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }
    singleOf(::FirebaseService)
    singleOf(::FirebaseAuthManager)
    singleOf(::FirebaseStoreManager)
}