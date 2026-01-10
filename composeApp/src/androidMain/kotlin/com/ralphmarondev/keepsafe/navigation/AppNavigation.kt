package com.ralphmarondev.keepsafe.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.keepsafe.features.auth.presentation.login.LoginScreenRoot
import com.ralphmarondev.keepsafe.features.auth.presentation.register.RegisterScreenRoot

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: Routes = Routes.Login
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Routes.Login> {
            LoginScreenRoot(
                onLoginSuccess = {
                    Log.d("Navigation", "Login success")
                },
                onRegisterClick = {
                    navController.navigate(Routes.Register) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Routes.Register> {
            RegisterScreenRoot(
                navigateToLogin = {
                    navController.navigate(Routes.Login) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}