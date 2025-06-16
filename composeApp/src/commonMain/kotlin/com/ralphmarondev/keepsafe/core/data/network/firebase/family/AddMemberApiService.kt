package com.ralphmarondev.keepsafe.core.data.network.firebase.family

import com.ralphmarondev.keepsafe.core.data.network.firebase.auth.RegisterApiService
import com.ralphmarondev.keepsafe.core.data.network.firebase.auth.RegisterRequest
import io.ktor.client.HttpClient

class AddMemberApiService(
    private val httpClient: HttpClient,
    private val registerApiService: RegisterApiService
) {
    suspend fun addMember(
        registerRequest: RegisterRequest,
        memberFields: MemberFields
    ) {
        val registerResponse = registerApiService.register(registerRequest)


    }
}