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
        private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme_key")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val FAMILY_ID_KEY = stringPreferencesKey("family_id")
        private val FULL_NAME_KEY = stringPreferencesKey("full_name")
        private val ROLE_KEY = stringPreferencesKey("role")
        private val UID_KEY = stringPreferencesKey("uid")

        const val DATA_STORE_FILE_NAME = "prefs.preferences_pb"

        fun create(producePath: () -> String): AppPreferences {
            val dataStore = PreferenceDataStoreFactory.createWithPath(
                produceFile = { producePath().toPath() }
            )
            return AppPreferences(dataStore)
        }
    }

    fun isDarkTheme(): Flow<Boolean> {
        return dataStore.data.map { prefs ->
            prefs[DARK_THEME_KEY] == true
        }
    }

    suspend fun setDarkTheme(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[DARK_THEME_KEY] = enabled
        }
    }

    suspend fun setEmail(email: String) {
        dataStore.edit { prefs ->
            prefs[EMAIL_KEY] = email
        }
    }

    suspend fun setUid(uid: String) {
        dataStore.edit { prefs ->
            prefs[UID_KEY] = uid
        }
    }

    suspend fun setFamilyId(familyId: String) {
        dataStore.edit { prefs ->
            prefs[FAMILY_ID_KEY] = familyId
        }
    }

    suspend fun setRole(role: String) {
        dataStore.edit { prefs ->
            prefs[ROLE_KEY] = role
        }
    }

    fun email(): Flow<String?> = dataStore.data.map { it[EMAIL_KEY] }
    fun familyId(): Flow<String?> = dataStore.data.map { it[FAMILY_ID_KEY] }
    fun fullName(): Flow<String?> = dataStore.data.map { it[FULL_NAME_KEY] }
    fun role(): Flow<String?> = dataStore.data.map { it[ROLE_KEY] }
    fun uid(): Flow<String?> = dataStore.data.map { it[UID_KEY] }
}