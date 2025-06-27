package com.ralphmarondev.keepsafe.core.util

import com.ralphmarondev.keepsafe.BuildConfig

actual object Secrets {
    actual val LOGIN_URL: String = BuildConfig.LOGIN_URL
    actual val REGISTER_URL: String = BuildConfig.REGISTER_URL
    actual val DATABASE_URL: String = BuildConfig.DATABASE_URL
    actual val ENCRYPTION_KEY: String = BuildConfig.ENCRYPTION_KEY
}