package com.ralphmarondev.keepsafe.feature.account.presentation.overview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OverviewScreenRoot(
    onLogout: () -> Unit
) {
    val viewModel: OverviewViewModel = koinViewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Account")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    viewModel.logout()
                    onLogout()
                }
            ) {
                Text(
                    text = "Logout",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}