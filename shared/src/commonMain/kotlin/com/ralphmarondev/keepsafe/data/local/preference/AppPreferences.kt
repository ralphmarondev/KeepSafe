package com.ralphmarondev.keepsafe.data.local.preference

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
        val USERNAME = stringPreferencesKey("username")
        val FAMILY_CODE = stringPreferencesKey("family_code")
        val FAMILY_NAME = stringPreferencesKey("family_name")

        const val DATASTORE_FILENAME = "keepsafe.preferences_pb"

        fun create(producePath: () -> String): AppPreferences {
            val dataStore = PreferenceDataStoreFactory.createWithPath(
                produceFile = { producePath().toPath() }
            )
            return AppPreferences(dataStore)
        }
    }

    suspend fun setDarkMode(value: Boolean) {
        dataStore.edit { it[DARK_MODE] = value }
    }

    val isDarkMode: Flow<Boolean> = dataStore.data.map { it[DARK_MODE] == true }

    suspend fun setUsername(username: String) {
        dataStore.edit { it[USERNAME] = username }
    }

    val username: Flow<String> = dataStore.data.map { it[USERNAME] ?: "" }

    suspend fun setFamilyCode(code: String) {
        dataStore.edit { it[FAMILY_CODE] = code }
    }

    val familyCode: Flow<String?> = dataStore.data.map { it[FAMILY_CODE] }

    suspend fun setFamilyName(name: String) {
        dataStore.edit { it[FAMILY_NAME] = name }
    }

    val familyName: Flow<String?> = dataStore.data.map { it[FAMILY_NAME] }

    suspend fun logout() {
        dataStore.edit {
            it.remove(USERNAME)
            it.remove(FAMILY_CODE)
            it.remove(FAMILY_NAME)
        }
    }
}