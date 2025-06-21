package com.ralphmarondev.keepsafe.core.data.repository

import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.domain.repository.PreferencesRepository

class PreferencesRepositoryImpl(
    private val preferences: AppPreferences
) : PreferencesRepository {

}