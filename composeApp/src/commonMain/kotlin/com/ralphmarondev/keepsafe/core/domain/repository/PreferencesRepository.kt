package com.ralphmarondev.keepsafe.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    fun isDarkTheme(): Flow<Boolean>
    suspend fun setDarkTheme(enabled: Boolean)

    fun firstLaunch(): Flow<Boolean>
    suspend fun setFirstLaunch(value: Boolean)

    fun notification(): Flow<Boolean>
    suspend fun setNotification(enabled: Boolean)

    fun hasAccount(): Flow<Boolean>
    suspend fun setHasAccount(value: Boolean)

    fun isFirstTimeReadingFamilyList(): Flow<Boolean>
    suspend fun setFirstTimeReadingFamilyList(value: Boolean)

    fun email(): Flow<String>
    suspend fun setEmail(email: String)

    fun uid(): Flow<String>
    suspend fun setUid(uid: String)

    fun familyId(): Flow<String>
    suspend fun setFamilyId(familyId: String)

    fun role(): Flow<String>
    suspend fun setRole(role: String)
}