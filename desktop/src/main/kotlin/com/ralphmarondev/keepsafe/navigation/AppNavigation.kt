package com.ralphmarondev.keepsafe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.ralphmarondev.keepsafe.feature.auth.presentation.login.LoginScreenRoot
import com.ralphmarondev.keepsafe.feature.auth.presentation.register.RegisterScreenRoot

@Composable
fun AppNavigation(
    startDestination: Route = Route.Auth,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation<Route.Auth>(
            startDestination = Route.Login
        ) {
            composable<Route.Login> {
                LoginScreenRoot(
                    onRegister = {
                        navController.navigate(Route.Register) {
                            popUpTo(Route.Login) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<Route.Register> {
                RegisterScreenRoot(
                    onLogin = {
                        navController.navigate(Route.Login) {
                            popUpTo(Route.Register) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}