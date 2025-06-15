package com.ralphmarondev.keepsafe.core.util

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect fun isDesktop(): Boolean