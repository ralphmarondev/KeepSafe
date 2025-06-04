package com.ralphmarondev.keepsafe.core.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import platform.Foundation.NSHomeDirectory

fun createDataStore(): DataStore<Preferences> {
    return createDataStore {
        NSHomeDirectory() + "/$DATA_STORE_FILE_NAME"
    }
}
