package com.ralphmarondev.keepsafe.di

import com.ralphmarondev.keepsafe.data.network.firebase.AuthService
import com.ralphmarondev.keepsafe.data.network.firebase.MemberService
import com.ralphmarondev.keepsafe.data.repository.AuthRepositoryImpl
import com.ralphmarondev.keepsafe.data.repository.MemberRepositoryImpl
import com.ralphmarondev.keepsafe.domain.repository.AuthRepository
import com.ralphmarondev.keepsafe.domain.repository.MemberRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    includes(platformModule)

    single<FirebaseAuth> { Firebase.auth }
    single<FirebaseFirestore> { Firebase.firestore }

    singleOf(::AuthService)
    singleOf(::MemberService)

    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    singleOf(::MemberRepositoryImpl).bind<MemberRepository>()
}