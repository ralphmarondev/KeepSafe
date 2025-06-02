package com.ralphmarondev.keepsafe

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.ralphmarondev.keepsafe.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "KeepSafe",
        ) {
            App()
        }
    }
}