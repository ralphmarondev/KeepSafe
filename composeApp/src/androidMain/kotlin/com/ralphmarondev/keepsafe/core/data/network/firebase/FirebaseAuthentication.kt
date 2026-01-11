package com.ralphmarondev.keepsafe.core.data.network.firebase

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuthentication(
    private val auth: FirebaseAuth
) {
    suspend fun login(email: String, password: String): AuthResult {
        return auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun register(email: String, password: String): AuthResult {
        return auth.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun logout() {
        auth.signOut()
    }

    fun currentUser() = auth.currentUser
}