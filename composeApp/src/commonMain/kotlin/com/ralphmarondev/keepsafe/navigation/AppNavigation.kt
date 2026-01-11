package com.ralphmarondev.keepsafe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ralphmarondev.keepsafe.features.auth.presentation.login.LoginScreenRoot
import com.ralphmarondev.keepsafe.features.auth.presentation.register.RegisterScreenRoot
import com.ralphmarondev.keepsafe.features.download.presentation.DownloadScreenRoot
import com.ralphmarondev.keepsafe.features.members.presentation.member_details.MemberDetailScreenRoot
import com.ralphmarondev.keepsafe.features.members.presentation.member_list.MemberListScreenRoot
import com.ralphmarondev.keepsafe.features.members.presentation.new_member.NewMemberScreenRoot

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
                    navController.navigate(Routes.Download) {
                        popUpTo(Routes.Login) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
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
        composable<Routes.Download> {
            DownloadScreenRoot(
                onDownloadCompleted = {
                    navController.navigate(Routes.MemberList) {
                        popUpTo(Routes.Download) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Routes.MemberList> {
            MemberListScreenRoot(
                onFamilyMemberClick = { email ->
                    navController.navigate(Routes.MemberDetail(email)) {
                        launchSingleTop = true
                    }
                },
                onAccountClick = {

                },
                onNotificationClick = {

                },
                onAddNewFamilyMemberClick = {
                    navController.navigate(Routes.NewMember) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Routes.MemberDetail> {
            val email = it.toRoute<Routes.MemberDetail>().email
            MemberDetailScreenRoot(
                email = email,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<Routes.NewMember> {
            NewMemberScreenRoot(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}