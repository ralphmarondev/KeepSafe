package com.ralphmarondev.keepsafe.auth.data.repository

import com.ralphmarondev.keepsafe.auth.domain.model.LoginResult
import com.ralphmarondev.keepsafe.auth.domain.repository.AuthRepository
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.data.network.firebase.auth.LoginApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.auth.LoginRequest
import com.ralphmarondev.keepsafe.core.data.network.firebase.family.GetDetailsApiService

class AuthRepositoryImpl(
    private val loginApiService: LoginApiService,
    private val getDetailsApiService: GetDetailsApiService,
    private val preferences: AppPreferences
) : AuthRepository {
    override suspend fun login(username: String, password: String): LoginResult? {
        val response = loginApiService.login(
            request = LoginRequest(
                email = username,
                password = password
            )
        )
        val userDetails = getDetailsApiService.getDetailsByEmail(email = response?.email ?: "")
        val isDeleted = userDetails?.member?.fields?.isDeleted?.booleanValue
        val familyId = userDetails?.member?.fields?.familyId?.stringValue
        return LoginResult(
            email = response?.email ?: "",
            uid = userDetails?.uid ?: "",
            role = userDetails?.member?.fields?.role?.stringValue ?: "",
            familyId = familyId ?: "",
            isDeleted = isDeleted != false
        )
    }

    override suspend fun saveLoginResult(result: LoginResult) {
        preferences.setEmail(email = result.email)
        preferences.setUid(uid = result.uid)
        preferences.setRole(role = result.role)
        preferences.setFamilyId(familyId = result.familyId)

        preferences.setHasAccountKey(true)
        preferences.setFirstLaunch(false)
    }
}