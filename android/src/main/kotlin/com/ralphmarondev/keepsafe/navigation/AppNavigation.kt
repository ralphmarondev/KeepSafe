package com.ralphmarondev.keepsafe.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.ralphmarondev.keepsafe.feature.auth.presentation.register.RegisterScreenRoot
import com.ralphmarondev.keepsafe.feature.auth.presentation.login.LoginScreenRoot

@Composable
fun AppNavigation(
    startDestination: Route = Route.Auth,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation<Route.Auth>(
            startDestination = Route.Register
        ) {
            composable<Route.Login> {
                LoginScreenRoot(
                    onSuccess = {
                        navController.navigate(Route.Main) {
                            popUpTo(Route.Auth) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onRegister = {
                        navController.navigate(Route.Register) {
                            popUpTo(Route.Auth) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<Route.Register> {
                RegisterScreenRoot(
                    onSuccess = {
                        navController.navigate(Route.Main) {
                            popUpTo(Route.Auth) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onLogin = {
                        navController.navigate(Route.Login) {
                            popUpTo(Route.Auth) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }

        navigation<Route.Main>(
            startDestination = Route.Dashboard
        ) {
            composable<Route.Dashboard> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Dashboard",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}