package com.ralphmarondev.keepsafe

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.ralphmarondev.keepsafe.core.theme.KeepSafeTheme
import com.ralphmarondev.keepsafe.navigation.AppNavigation
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App() {
    val viewModel: AppSettingsViewModel = koinViewModel()
    val isDarkMode = viewModel.darkMode.collectAsState().value

    KeepSafeTheme(
        darkTheme = isDarkMode
    ) {
        KoinContext {
            AppNavigation()
        }
    }
}