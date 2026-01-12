package com.ralphmarondev.keepsafe.core.common

enum class Platform {
    Android,
    Ios,
    Desktop
}

enum class Desktop {
    Windows,
    MacOS,
    Linux,
    Unknown
}

expect object Environment {
    val platform: Platform
    val desktop: Desktop

    val isAndroid: Boolean
    val isIos: Boolean
    val isDesktop: Boolean

    val isWindows: Boolean
    val isMacOS: Boolean
    val isLinux: Boolean


    val languageCode: String
    val regionCode: String
    val timeZoneId: String

    fun currentTimeMillis(): Long
}