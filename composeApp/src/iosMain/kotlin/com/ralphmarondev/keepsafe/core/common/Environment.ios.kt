package com.ralphmarondev.keepsafe.core.common

actual class Environment {
    actual val platform: Platform
        get() = TODO("Not yet implemented")
    actual val desktop: Desktop
        get() = TODO("Not yet implemented")
    actual val isAndroid: Boolean
        get() = TODO("Not yet implemented")
    actual val isIos: Boolean
        get() = TODO("Not yet implemented")
    actual val isDesktop: Boolean
        get() = TODO("Not yet implemented")
    actual val isWindows: Boolean
        get() = TODO("Not yet implemented")
    actual val isMacOS: Boolean
        get() = TODO("Not yet implemented")
    actual val isLinux: Boolean
        get() = TODO("Not yet implemented")
    actual val languageCode: String
        get() = TODO("Not yet implemented")
    actual val regionCode: String
        get() = TODO("Not yet implemented")
    actual val timeZoneId: String
        get() = TODO("Not yet implemented")

    actual fun currentTimeMillis(): Long {
        TODO("Not yet implemented")
    }
}