package com.ralphmarondev.keepsafe.core.common

import com.ralphmarondev.keepsafe.BuildConfig

actual object Constants {
    actual object Firebase {
        actual val API_KEY: String = BuildConfig.API_KEY
        actual val PROJECT_ID: String = BuildConfig.PROJECT_ID
    }
}