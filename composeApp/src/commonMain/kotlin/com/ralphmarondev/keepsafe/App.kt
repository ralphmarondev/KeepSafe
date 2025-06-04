package com.ralphmarondev.keepsafe

import androidx.compose.runtime.Composable
import com.ralphmarondev.keepsafe.core.theme.KeepSafeTheme
import com.ralphmarondev.keepsafe.navigation.AppNavigation
import org.koin.compose.KoinContext

@Composable
fun App() {
    KeepSafeTheme {
        KoinContext {
            AppNavigation()
        }
    }
}