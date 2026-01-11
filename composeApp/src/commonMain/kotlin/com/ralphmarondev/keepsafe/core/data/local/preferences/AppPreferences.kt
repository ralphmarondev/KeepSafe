package com.ralphmarondev.keepsafe.core.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath

class AppPreferences(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val IS_AUTHENTICATED = booleanPreferencesKey("is_authenticated")
        val FAMILY_ID = stringPreferencesKey("family_id")
        val IS_FAMILY_ADMIN = booleanPreferencesKey("is_admin")
        val CURRENT_USER = stringPreferencesKey("current_user_email")
        val ID_TOKEN = stringPreferencesKey("id_token")

        const val DATA_STORE_FILE_NAME = "prefs.preferences_pb"

        fun create(producePath: () -> String): AppPreferences {
            val dataStore = PreferenceDataStoreFactory.createWithPath(
                produceFile = { producePath().toPath() }
            )
            return AppPreferences(dataStore)
        }
    }

    suspend fun setDarkTheme(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[DARK_MODE] = enabled
        }
    }

    fun isDarkTheme(): Flow<Boolean> = dataStore.data.map {
        it[DARK_MODE] ?: false
    }

    suspend fun setDarkMode(value: Boolean) {
        dataStore.edit { it[DARK_MODE] = value }
    }

    val isDarkMode: Flow<Boolean> = dataStore.data.map { it[DARK_MODE] == true }

    suspend fun setOnboardingCompleted(value: Boolean) {
        dataStore.edit { it[ONBOARDING_COMPLETED] = value }
    }

    val isOnboardingCompleted: Flow<Boolean> = dataStore.data.map {
        it[ONBOARDING_COMPLETED] ?: false
    }

    suspend fun setFamilyId(value: String) {
        dataStore.edit { it[FAMILY_ID] = value }
    }

    val familyId: Flow<String> = dataStore.data.map { it[FAMILY_ID] ?: "" }

    suspend fun setCurrentUserRole(value: Boolean) {
        dataStore.edit { it[IS_FAMILY_ADMIN] = value }
    }

    val isCurrentUserFamilyAdmin: Flow<Boolean> = dataStore.data.map {
        it[IS_FAMILY_ADMIN] ?: false
    }

    suspend fun setCurrentUserEmail(value: String) {
        dataStore.edit { it[CURRENT_USER] = value }
    }

    val currentUserEmail: Flow<String> = dataStore.data.map {
        it[CURRENT_USER] ?: ""
    }

    suspend fun setIsAuthenticated(value: Boolean) {
        dataStore.edit { it[IS_AUTHENTICATED] = value }
    }

    val isAuthenticated: Flow<Boolean> = dataStore.data.map {
        it[IS_AUTHENTICATED] ?: false
    }

    suspend fun setIdToken(value: String) {
        dataStore.edit { it[ID_TOKEN] = value }
    }

    val idToken: Flow<String> = dataStore.data.map {
        it[ID_TOKEN] ?: ""
    }
}