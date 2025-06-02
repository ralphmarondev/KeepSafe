package com.ralphmarondev.keepsafe

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.keepsafe.auth.presentation.login.LoginScreen
import org.koin.compose.KoinContext

@Composable
fun App() {
    val navController = rememberNavController()

    MaterialTheme {
        KoinContext {
            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                composable("login") {
                    LoginScreen()
                }
            }
        }
    }
}