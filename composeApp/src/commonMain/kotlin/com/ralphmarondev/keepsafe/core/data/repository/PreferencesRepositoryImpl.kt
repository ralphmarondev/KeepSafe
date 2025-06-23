package com.ralphmarondev.keepsafe.core.data.repository

import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class PreferencesRepositoryImpl(
    private val preferences: AppPreferences
) : PreferencesRepository {

    override fun isDarkTheme(): Flow<Boolean> {
        return preferences.isDarkTheme()
    }

    override suspend fun setDarkTheme(enabled: Boolean) {
        preferences.setDarkTheme(enabled)
    }

    override fun firstLaunch(): Flow<Boolean> {
        return preferences.isDarkTheme()
    }

    override suspend fun setFirstLaunch(value: Boolean) {
        preferences.setFirstLaunch(value)
    }

    override fun notification(): Flow<Boolean> {
        return preferences.notification()
    }

    override suspend fun setNotification(enabled: Boolean) {
        preferences.setFirstLaunch(enabled)
    }

    override fun hasAccount(): Flow<Boolean> {
        return preferences.hasAccount()
    }

    override suspend fun setHasAccount(value: Boolean) {
        preferences.setHasAccountKey(value)
    }

    override fun isFirstTimeReadingFamilyList(): Flow<Boolean> {
        return preferences.isFirstTimeReadingFamilyList()
    }

    override suspend fun setFirstTimeReadingFamilyList(value: Boolean) {
        preferences.setFirstTimeReadingFamilyList(value)
    }

    override fun email(): Flow<String> {
        return preferences.email()
    }

    override suspend fun setEmail(email: String) {
        preferences.setEmail(email)
    }

    override fun uid(): Flow<String> {
        return preferences.uid()
    }

    override suspend fun setUid(uid: String) {
        preferences.setUid(uid)
    }

    override fun familyId(): Flow<String> {
        return preferences.familyId()
    }

    override suspend fun setFamilyId(familyId: String) {
        preferences.setFamilyId(familyId)
    }

    override fun role(): Flow<String> {
        return preferences.role()
    }

    override suspend fun setRole(role: String) {
        preferences.setRole(role)
    }
}