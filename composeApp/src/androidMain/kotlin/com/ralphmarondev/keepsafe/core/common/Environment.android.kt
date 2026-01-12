package com.ralphmarondev.keepsafe.core.common

import java.util.Locale
import java.util.TimeZone

actual object Environment {
    actual val platform: Platform = Platform.Android
    actual val desktop: Desktop = Desktop.Unknown
    actual val isAndroid: Boolean = true
    actual val isIos: Boolean = false
    actual val isDesktop: Boolean = false
    actual val isWindows: Boolean = false
    actual val isMacOS: Boolean = false
    actual val isLinux: Boolean = false
    actual val languageCode: String
        get() = Locale.getDefault().language
    actual val regionCode: String
        get() = Locale.getDefault().country
    actual val timeZoneId: String
        get() = TimeZone.getDefault().id

    actual fun currentTimeMillis(): Long = System.currentTimeMillis()
}