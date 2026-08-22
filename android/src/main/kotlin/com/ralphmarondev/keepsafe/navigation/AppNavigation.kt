package com.ralphmarondev.keepsafe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.ralphmarondev.keepsafe.feature.auth.presentation.login.LoginScreenRoot

@Composable
fun AppNavigation(
    startDestination: Route = Route.Auth.Root,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation<Route.Auth.Root>(
            startDestination = Route.Auth.Login
        ) {
            composable<Route.Auth.Login> {
                LoginScreenRoot(
                    onSuccess = {
                        navController.navigate(Route.Main.Root) {
                            popUpTo(Route.Auth.Root) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onRegister = {}
                )
            }
            composable<Route.Auth.Register> {

            }
        }

        navigation<Route.Main.Root>(
            startDestination = Route.Main.Dashboard
        ) {
            composable<Route.Main.Dashboard> {

            }
        }
    }
}