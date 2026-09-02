package com.ralphmarondev.keepsafe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.ralphmarondev.keepsafe.feature.account.presentation.overview.OverviewScreenRoot
import com.ralphmarondev.keepsafe.feature.auth.presentation.login.LoginScreenRoot
import com.ralphmarondev.keepsafe.feature.auth.presentation.register.RegisterScreenRoot
import com.ralphmarondev.keepsafe.feature.family.presentation.member_detail.MemberDetailScreenRoot
import com.ralphmarondev.keepsafe.feature.family.presentation.member_list.MemberListScreenRoot
import com.ralphmarondev.keepsafe.feature.family.presentation.new_member.NewMemberScreenRoot

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
            startDestination = Route.MemberList
        ) {
            composable<Route.MemberList> {
                MemberListScreenRoot(
                    profile = {
                        navController.navigate(Route.Account) {
                            launchSingleTop = true
                        }
                    },
                    newMember = {
                        navController.navigate(Route.NewMember) {
                            launchSingleTop = true
                        }
                    },
                    memberDetail = { memberUid ->
                        navController.navigate(Route.MemberDetail(memberUid)) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<Route.MemberDetail> {
                val uid = it.toRoute<Route.MemberDetail>().uid
                MemberDetailScreenRoot(uid)
            }
            composable<Route.NewMember> {
                NewMemberScreenRoot(
                    onSuccess = {
                        navController.navigateUp()
                    }
                )
            }
        }

        navigation<Route.Account>(
            startDestination = Route.Overview
        ) {
            composable<Route.Overview> {
                OverviewScreenRoot(
                    onLogout = {
                        navController.navigate(Route.Auth) {
                            popUpTo(Route.Main) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}