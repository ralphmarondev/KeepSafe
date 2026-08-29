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
                LoginScreenRoot()
            }
        }
    }
}