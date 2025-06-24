package com.ralphmarondev.keepsafe

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.ralphmarondev.keepsafe.core.theme.ThemeState
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val themeState: ThemeState = get()
            val darkMode = themeState.darkMode.collectAsState().value
            val view = LocalView.current

            if (!view.isInEditMode) {
                SideEffect {
                    val window = (view.context as Activity).window
                    val insetsController = window?.let {
                        WindowCompat.getInsetsController(it, view)
                    }
                    insetsController?.isAppearanceLightStatusBars = !darkMode
                }
            }

            App(themeState = themeState)
        }
    }
}