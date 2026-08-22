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
    startDestination: Route = Route.Auth,
    navController: NavHostController = rememberNavController()
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
                    onSuccess = {
                        navController.navigate(Route.Main) {
                            popUpTo(Route.Auth) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onRegister = {}
                )
            }
            composable<Route.Register> {

            }
        }

        navigation<Route.Main>(
            startDestination = Route.Dashboard
        ) {
            composable<Route.Dashboard> {

            }
        }
    }
}