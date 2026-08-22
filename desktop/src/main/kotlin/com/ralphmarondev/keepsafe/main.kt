package com.ralphmarondev.keepsafe

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.google.firebase.FirebasePlatform
import com.ralphmarondev.keepsafe.di.sharedModule
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize
import org.koin.core.context.startKoin

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
            modules(sharedModule)
        }

        Window(
            onCloseRequest = ::exitApplication,
            title = "Keepsafe",
        ) {
            MaterialTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Keepsafe",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}