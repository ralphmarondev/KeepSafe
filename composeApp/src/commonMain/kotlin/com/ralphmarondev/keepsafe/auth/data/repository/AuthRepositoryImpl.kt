package com.ralphmarondev.keepsafe.auth.data.repository

import com.ralphmarondev.keepsafe.auth.domain.model.LoginResult
import com.ralphmarondev.keepsafe.auth.domain.repository.AuthRepository
import com.ralphmarondev.keepsafe.core.data.network.firebase.auth.LoginApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.auth.LoginRequest
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.GetDetailsApiService

class AuthRepositoryImpl(
    private val loginApiService: LoginApiService,
    private val getDetailsApiService: GetDetailsApiService
) : AuthRepository {
    override suspend fun login(username: String, password: String): LoginResult? {
        val response = loginApiService.login(
            request = LoginRequest(
                email = username,
                password = password
            )
        )
        val userDetails = getDetailsApiService.getDetailsByEmail(email = response?.email ?: "")

        return LoginResult(
            email = response?.email ?: "",
            uid = userDetails?.uid ?: "",
            role = userDetails?.member?.fields?.role?.stringValue ?: ""
        )
    }
}