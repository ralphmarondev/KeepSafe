package com.ralphmarondev.keepsafe.core.util

import com.ralphmarondev.keepsafe.BuildConfig

actual object Secrets {
    actual val apiKey: String = BuildConfig.API_KEY
    actual val authUrl: String = BuildConfig.AUTHENTICATION_URL
    actual val familyListUrl: String = BuildConfig.FAMILY_LIST_URL
    actual val userDetailsUrl: String = BuildConfig.USER_DETAILS_URL
}