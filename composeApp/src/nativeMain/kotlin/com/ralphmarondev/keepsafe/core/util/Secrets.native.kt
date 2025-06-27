package com.ralphmarondev.keepsafe.core.util

import platform.Foundation.NSProcessInfo

actual object Secrets {

    private fun getEnv(key: String): String =
        NSProcessInfo.processInfo.environment?.get(key)?.toString()
            ?: error("Missing environment variable: $key")

    actual val LOGIN_URL: String = getEnv("AUTHENTICATION_URL")
    actual val REGISTER_URL: String = getEnv("FAMILY_LIST_URL")
    actual val DATABASE_URL: String = getEnv("USER_DETAILS_URL")
    actual val ENCRYPTION_KEY: String = getEnv("ENCRYPTION_KEY")
}