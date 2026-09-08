package com.ralphmarondev.keepsafe.core.common

import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon

object NotificationHelper {
    private var trayIcon: TrayIcon? = null

    fun initialize() {
        if (!SystemTray.isSupported()) {
            println("System tray is not supported.")
            return
        }

        if (trayIcon != null) return

        val image = Toolkit.getDefaultToolkit()
            .createImage(ByteArray(0))

        trayIcon = TrayIcon(image, "Keepsafe").apply {
            isImageAutoSize = true
        }
        SystemTray.getSystemTray().add(trayIcon)
    }

    fun show(title: String, message: String) {
        trayIcon?.displayMessage(
            title,
            message,
            TrayIcon.MessageType.INFO
        )
    }
}