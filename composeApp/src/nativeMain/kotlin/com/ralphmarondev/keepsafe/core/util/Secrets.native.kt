package com.ralphmarondev.keepsafe.core.util

import platform.Foundation.NSProcessInfo

actual object Secrets {

    private fun getEnv(key: String): String =
        NSProcessInfo.processInfo.environment?.get(key)?.toString()
            ?: error("Missing environment variable: $key")

    actual val apiKey: String = getEnv("API_KEY")
    actual val authUrl: String = getEnv("AUTHENTICATION_URL")
    actual val familyListUrl: String = getEnv("FAMILY_LIST_URL")
}