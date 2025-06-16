package com.ralphmarondev.keepsafe.core.util

interface NotificationService {
    fun showNotification(title: String, message: String)
}

expect fun getNotificationService(): NotificationService