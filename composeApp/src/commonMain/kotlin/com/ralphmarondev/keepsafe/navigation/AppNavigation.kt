package com.ralphmarondev.keepsafe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ralphmarondev.keepsafe.auth.presentation.login.LoginScreen
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.family.presentation.member_detail.FamilyMemberDetailScreen
import com.ralphmarondev.keepsafe.family.presentation.new_member.NewFamilyMemberScreen
import com.ralphmarondev.keepsafe.family.presentation.update_member.UpdateFamilyMemberScreen
import com.ralphmarondev.keepsafe.home.presentation.HomeScreen
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject

@Serializable
sealed interface Routes {

    @Serializable
    data object Login : Routes

    @Serializable
    data object Home : Routes

    @Serializable
    data object NewFamilyMember : Routes

    @Serializable
    data class FamilyMemberDetail(val id: String) : Routes

    @Serializable
    data class UpdateFamilyMember(val uid: String) : Routes
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
) {
    val preferences = koinInject<AppPreferences>()
    val isFirstLaunch = preferences.firstLaunch().collectAsState(initial = true).value
    val hasAccount = preferences.hasAccount().collectAsState(initial = false).value

    val startDestination: Any = if (isFirstLaunch == true) {
        Routes.Login // onboarding later :)
    } else {
        if (hasAccount == true) {
            Routes.Home
        } else {
            Routes.Login
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
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
                },
                navigateToFamilyMemberDetails = { id ->
                    navController.navigate(Routes.FamilyMemberDetail(id)) {
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
        composable<Routes.FamilyMemberDetail> {
            val id = it.toRoute<Routes.FamilyMemberDetail>().id

            FamilyMemberDetailScreen(
                memberId = id,
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToUpdate = { uid ->
                    navController.navigate(Routes.UpdateFamilyMember(uid)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Routes.UpdateFamilyMember> {
            val uid = it.toRoute<Routes.UpdateFamilyMember>().uid
            UpdateFamilyMemberScreen(
                uid = uid,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}