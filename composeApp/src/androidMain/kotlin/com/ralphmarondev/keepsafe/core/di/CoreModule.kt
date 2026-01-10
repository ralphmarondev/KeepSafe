package com.ralphmarondev.keepsafe.core.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.ralphmarondev.keepsafe.core.data.local.database.AppDatabase
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.data.network.FirebaseAuth
import com.ralphmarondev.keepsafe.core.data.network.FirebaseService
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreModule = module {
    singleOf(::AppPreferences)
    single { AppDatabase.createDatabase(androidContext()) }
    single { get<AppDatabase>().userDao }

    single { com.google.firebase.auth.FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }
    singleOf(::FirebaseAuth)
    singleOf(::FirebaseService)
}