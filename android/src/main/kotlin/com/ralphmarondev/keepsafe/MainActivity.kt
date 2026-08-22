package com.ralphmarondev.keepsafe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ralphmarondev.keepsafe.data.network.firebase.AuthService
import com.ralphmarondev.keepsafe.data.network.firebase.FirestoreService
import com.ralphmarondev.keepsafe.domain.model.Account
import com.ralphmarondev.keepsafe.domain.model.Family
import com.ralphmarondev.keepsafe.domain.model.Result
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val authService: AuthService by inject()
    private val firestoreService: FirestoreService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaunchedEffect(Unit) {
                val sampleAccount = Account(
                    username = "android",
                    password = "password123"
                )
                when (val result = authService.register(sampleAccount)) {
                    is Result.Success -> {
                        Log.d("Keepsafe", "Success! Created account with UID: ${result.data.uid}")
                    }

                    is Result.Error -> {
                        Log.e("Keepsafe", "Error: ${result.message}", result.throwable)
                    }
                }

                val sampleFamily = Family(
                    name = "Android Family",
                    code = "MARON123"
                )

                when (val result = firestoreService.createFamily(sampleFamily)) {
                    is Result.Success -> {
                        println("[TEST_FIRESTORE] Created Family UID: ${result.data.uid}")
                    }

                    is Result.Error -> {
                        println("[TEST_FIRESTORE] Error: ${result.message}")
                    }
                }
            }

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