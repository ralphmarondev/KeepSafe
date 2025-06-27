package com.ralphmarondev.keepsafe.core.util

import platform.UIKit.UIDevice

class NativePlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = NativePlatform()
actual fun isDesktop(): Boolean = false