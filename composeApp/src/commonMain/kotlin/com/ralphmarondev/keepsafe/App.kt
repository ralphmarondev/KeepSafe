package com.ralphmarondev.keepsafe

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.ralphmarondev.keepsafe.navigation.AppNavigation
import org.koin.compose.KoinContext

@Composable
fun App() {
    MaterialTheme {
        KoinContext {
            AppNavigation()
        }
    }
}