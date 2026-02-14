package com.ralphmarondev.androidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ralphmarondev.keepsafe.App
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val preferences: AppPreferences = get()
            App(preferences = preferences)
        }
    }
}