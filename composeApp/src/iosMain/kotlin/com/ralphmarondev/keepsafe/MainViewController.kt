package com.ralphmarondev.keepsafe

import androidx.compose.ui.window.ComposeUIViewController
import com.ralphmarondev.keepsafe.di.initKoin

fun MainViewController() {
    ComposeUIViewController(
        configure = { initKoin() }
    ) {
//        App()
    }
}
