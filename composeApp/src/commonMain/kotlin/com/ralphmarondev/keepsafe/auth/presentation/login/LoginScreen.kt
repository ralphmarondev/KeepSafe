package com.ralphmarondev.keepsafe.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun LoginScreen(
    navigateToHome: () -> Unit
) {
    val viewModel = koinViewModel<LoginViewModel>()
    val username = viewModel.username.collectAsState().value
    val password = viewModel.password.collectAsState().value
    val response = viewModel.response.collectAsState().value

    LaunchedEffect(response) {
        response?.let {
            if (it.success) {
                navigateToHome()
                viewModel.resetResponse()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Login"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                OutlinedCard(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        OutlinedTextField(
                            value = username,
                            onValueChange = viewModel::onUsernameValueChange,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            placeholder = {
                                Text(
                                    text = "Username"
                                )
                            }
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = viewModel::onPasswordValueChange,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            placeholder = {
                                Text(
                                    text = "Password"
                                )
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = viewModel::login,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "LOGIN"
                            )
                        }
                    }
                }
            }
        }
    }
}