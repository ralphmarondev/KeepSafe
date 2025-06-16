package com.ralphmarondev.keepsafe.core.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okio.Path.Companion.toPath

class AppPreferences(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme_key")
        private val ID_TOKEN_KEY = stringPreferencesKey("id_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        private val LOCAL_ID_KEY = stringPreferencesKey("local_id")
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

    suspend fun setRole(role: String){
        dataStore.edit { prefs->
            prefs[ROLE_KEY] = role
        }
    }

    fun saveAuthTokens(
        idToken: String,
        refreshToken: String?,
        localId: String,
        email: String,
        familyId: String,
        fullName: String,
        role: String
    ) {
        runBlocking {
            dataStore.edit {
                it[ID_TOKEN_KEY] = idToken
                refreshToken?.let { token -> it[REFRESH_TOKEN_KEY] = token }
                it[LOCAL_ID_KEY] = localId
                it[EMAIL_KEY] = email
                it[FAMILY_ID_KEY] = familyId
                it[FULL_NAME_KEY] = fullName
                it[ROLE_KEY] = role
            }
        }
    }

    fun clearAuthTokens() {
        runBlocking {
            dataStore.edit {
                it.remove(ID_TOKEN_KEY)
                it.remove(REFRESH_TOKEN_KEY)
                it.remove(LOCAL_ID_KEY)
                it.remove(EMAIL_KEY)
                it.remove(FAMILY_ID_KEY)
                it.remove(FULL_NAME_KEY)
                it.remove(ROLE_KEY)
            }
        }
    }

    fun idToken(): Flow<String?> = dataStore.data.map { it[ID_TOKEN_KEY] }
    fun refreshToken(): Flow<String?> = dataStore.data.map { it[REFRESH_TOKEN_KEY] }
    fun localId(): Flow<String?> = dataStore.data.map { it[LOCAL_ID_KEY] }
    fun email(): Flow<String?> = dataStore.data.map { it[EMAIL_KEY] }
    fun familyId(): Flow<String?> = dataStore.data.map { it[FAMILY_ID_KEY] }
    fun fullName(): Flow<String?> = dataStore.data.map { it[FULL_NAME_KEY] }
    fun role(): Flow<String?> = dataStore.data.map { it[ROLE_KEY] }
    fun uid(): Flow<String?> = dataStore.data.map { it[UID_KEY] }
}