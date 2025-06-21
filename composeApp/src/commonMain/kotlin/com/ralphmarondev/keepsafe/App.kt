package com.ralphmarondev.keepsafe

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.theme.KeepSafeTheme
import com.ralphmarondev.keepsafe.core.theme.LocalThemeState
import com.ralphmarondev.keepsafe.core.theme.ThemeState
import com.ralphmarondev.keepsafe.navigation.AppNavigation
import com.ralphmarondev.keepsafe.navigation.Routes
import keepsafe.composeapp.generated.resources.Res
import keepsafe.composeapp.generated.resources.keepsafe_logo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App(
    themeState: ThemeState
) {
    CompositionLocalProvider(
        LocalThemeState provides themeState
    ) {
        KeepSafeTheme(
            darkTheme = themeState.darkMode.collectAsState().value
        ) {
            val preferences = koinInject<AppPreferences>()
            var isFirstLaunch by remember { mutableStateOf<Boolean?>(null) }
            var hasAccount by remember { mutableStateOf<Boolean?>(null) }

            LaunchedEffect(Unit) {
                delay(1500)
                isFirstLaunch = preferences.firstLaunch().first() != false
                hasAccount = preferences.hasAccount().first() == true
            }

            if (isFirstLaunch == null || hasAccount == null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(Res.drawable.keepsafe_logo),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(140.dp)
                    )
                    Text(
                        text = "v2025.06",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                }
            } else {
                val startDestination: Any = if (isFirstLaunch == true) {
                    Routes.Login // onboarding later :)
                } else {
                    if (hasAccount == true) {
                        Routes.Home
                    } else {
                        Routes.Login
                    }
                }
                AppNavigation(startDestination = startDestination)
            }
        }
    }
}