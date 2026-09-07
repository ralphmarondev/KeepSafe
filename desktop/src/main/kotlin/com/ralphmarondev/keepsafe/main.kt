package com.ralphmarondev.keepsafe

import android.app.Application
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.google.firebase.FirebasePlatform
import com.ralphmarondev.keepsafe.data.local.preference.AppPreferences
import com.ralphmarondev.keepsafe.di.appModule
import com.ralphmarondev.keepsafe.navigation.AppNavigation
import com.ralphmarondev.keepsafe.presentation.theme.KeepsafeTheme
import com.ralphmarondev.keepsafe.presentation.theme.LocalThemeState
import com.ralphmarondev.keepsafe.presentation.theme.ThemeProvider
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.core.context.startKoin
import java.awt.Dimension

@Suppress("DEPRECATION")
fun main() {
    application {
        FirebasePlatform.initializeFirebasePlatform(object : FirebasePlatform() {
            private val storage = mutableMapOf<String, String>()
            override fun store(key: String, value: String) = storage.set(key, value)
            override fun retrieve(key: String): String? = storage[key]
            override fun clear(key: String) {
                storage.remove(key)
            }

            override fun log(msg: String) = println("[Firebase JVM]: $msg")
        })

        Firebase.initialize(
            context = Application(),
            options = FirebaseOptions(
                apiKey = BuildConfig.FIREBASE_API_KEY,
                applicationId = BuildConfig.FIREBASE_APP_ID,
                projectId = BuildConfig.FIREBASE_PROJECT_ID
            )
        )

        startKoin {
            modules(appModule)
        }

        val windowState = rememberWindowState(
            size = DpSize(800.dp, 600.dp)
        )

        Window(
            onCloseRequest = ::exitApplication,
            title = "Keepsafe",
            state = windowState
        ) {
            window.minimumSize = Dimension(800, 600)

            KoinContext {
                val preferences: AppPreferences = koinInject()

                ThemeProvider(preferences = preferences) {
                    val themeState = LocalThemeState.current

                    KeepsafeTheme(darkTheme = themeState.darkMode.value) {
                        AppNavigation()
                    }
                }
            }
        }
    }
}