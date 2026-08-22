package com.ralphmarondev.keepsafe.di

import com.ralphmarondev.keepsafe.data.network.firebase.AuthService
import com.ralphmarondev.keepsafe.data.network.firebase.FirestoreService
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val sharedModule = module {
    includes(platformModule)

    single<FirebaseAuth> { Firebase.auth }
    single<FirebaseFirestore> { Firebase.firestore }

    singleOf(::AuthService)
    singleOf(::FirestoreService)
}