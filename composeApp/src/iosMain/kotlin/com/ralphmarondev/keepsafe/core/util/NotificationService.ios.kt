package com.ralphmarondev.keepsafe.core.util

class NativeNotificationService : NotificationService {
    override fun showNotification(title: String, message: String) {
        println("iOS notification requested: $title - $message (not yet implemented)")
    }
}

actual fun getNotificationService(): NotificationService = NativeNotificationService()