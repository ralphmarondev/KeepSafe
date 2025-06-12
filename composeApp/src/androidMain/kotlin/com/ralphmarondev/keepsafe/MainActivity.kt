package com.ralphmarondev.keepsafe

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.ralphmarondev.keepsafe.core.theme.ThemeState
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val themeState: ThemeState = get()
            App(themeState = themeState)
        }
    }
}