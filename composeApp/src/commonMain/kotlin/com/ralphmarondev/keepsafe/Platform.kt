package com.ralphmarondev.keepsafe

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform