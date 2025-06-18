package com.ralphmarondev.keepsafe

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.ralphmarondev.keepsafe.core.theme.ThemeState
import com.ralphmarondev.keepsafe.di.initKoin
import org.koin.core.context.GlobalContext.get

fun main() {
    initKoin()
    val themeState = get().get<ThemeState>()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "KeepSafe",
            icon = painterResource("icons/keepsafe_logo.png")
        ) {
            App(themeState = themeState)
        }
    }
}