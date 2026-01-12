package com.ralphmarondev.keepsafe.core.common

import java.util.Locale
import java.util.TimeZone

actual object Environment {

    private val os = System.getProperty("os.name")?.lowercase() ?: ""

    actual val platform: Platform = Platform.Desktop
    actual val desktop: Desktop = when {
        os.contains("win") -> Desktop.Windows
        os.contains("mac") -> Desktop.MacOS
        os.contains("nix") || os.contains("nux") || os.contains("linux") -> Desktop.Linux
        else -> Desktop.Unknown
    }
    actual val isAndroid: Boolean = false
    actual val isIos: Boolean = false
    actual val isDesktop: Boolean = true
    actual val isWindows: Boolean = desktop == Desktop.Windows
    actual val isMacOS: Boolean = desktop == Desktop.MacOS
    actual val isLinux: Boolean = desktop == Desktop.Linux
    actual val languageCode: String
        get() = Locale.getDefault().language
    actual val regionCode: String
        get() = Locale.getDefault().country
    actual val timeZoneId: String
        get() = TimeZone.getDefault().id

    actual fun currentTimeMillis(): Long = System.currentTimeMillis()
}