package com.ralphmarondev.keepsafe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.keepsafe.auth.presentation.login.LoginScreen
import com.ralphmarondev.keepsafe.home.presentation.HomeScreen
import com.ralphmarondev.keepsafe.new_family_member.presentation.NewFamilyMemberScreen
import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    @Serializable
    data object Login : Routes

    @Serializable
    data object Home : Routes

    @Serializable
    data object NewFamilyMember : Routes
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Login
    ) {
        composable<Routes.Login> {
            LoginScreen(
                navigateToHome = {
                    navController.navigate(Routes.Home) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Routes.Home> {
            HomeScreen(
                logout = {
                    navController.navigate(Routes.Login) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                navigateToNewFamilyMember = {
                    navController.navigate(Routes.NewFamilyMember) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Routes.NewFamilyMember> {
            NewFamilyMemberScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}