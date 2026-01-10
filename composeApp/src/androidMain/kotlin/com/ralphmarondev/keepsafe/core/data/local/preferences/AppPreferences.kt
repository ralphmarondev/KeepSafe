package com.ralphmarondev.keepsafe.core.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "keepsafe_preferences"
val Context.appDataStore by preferencesDataStore(name = DATASTORE_NAME)

class AppPreferences(
    private val context: Context
) {
    companion object {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val IS_AUTHENTICATED = booleanPreferencesKey("is_authenticated")
        val FAMILY_ID = stringPreferencesKey("family_id")
        val IS_FAMILY_ADMIN = booleanPreferencesKey("is_admin")
        val CURRENT_USER = stringPreferencesKey("current_user_email")
    }

    suspend fun setDarkMode(value: Boolean) {
        context.appDataStore.edit { it[DARK_MODE] = value }
    }

    val isDarkMode: Flow<Boolean> = context.appDataStore.data.map { it[DARK_MODE] == true }

    suspend fun setOnboardingCompleted(value: Boolean) {
        context.appDataStore.edit { it[ONBOARDING_COMPLETED] = value }
    }

    val isOnboardingCompleted: Flow<Boolean> = context.appDataStore.data.map {
        it[ONBOARDING_COMPLETED] ?: false
    }

    suspend fun setFamilyId(value: String) {
        context.appDataStore.edit { it[FAMILY_ID] = value }
    }

    val familyId: Flow<String> = context.appDataStore.data.map { it[FAMILY_ID] ?: "" }

    suspend fun setCurrentUserRole(value: Boolean) {
        context.appDataStore.edit { it[IS_FAMILY_ADMIN] = value }
    }

    val isCurrentUserFamilyAdmin: Flow<Boolean> = context.appDataStore.data.map {
        it[IS_FAMILY_ADMIN] ?: false
    }

    suspend fun setCurrentUserEmail(value: String) {
        context.appDataStore.edit { it[CURRENT_USER] = value }
    }

    val currentUserEmail: Flow<String> = context.appDataStore.data.map {
        it[CURRENT_USER] ?: ""
    }

    suspend fun setIsAuthenticated(value: Boolean) {
        context.appDataStore.edit { it[IS_AUTHENTICATED] = value }
    }

    val isAuthenticated: Flow<Boolean> = context.appDataStore.data.map {
        it[IS_AUTHENTICATED] ?: false
    }
}