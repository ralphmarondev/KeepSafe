package com.ralphmarondev.keepsafe.core.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createDataStore(): DataStore<Preferences>{
    return createDataStore {
        System.getProperty("user.home") + "/.$DATA_STORE_FILE_NAME"
    }
}