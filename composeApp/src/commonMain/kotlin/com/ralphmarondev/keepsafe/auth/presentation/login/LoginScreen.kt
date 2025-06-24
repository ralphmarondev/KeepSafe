package com.ralphmarondev.keepsafe.auth.presentation.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ralphmarondev.keepsafe.auth.presentation.components.PasswordTextField
import com.ralphmarondev.keepsafe.auth.presentation.components.UsernameTextField
import com.ralphmarondev.keepsafe.core.components.GradientSnackBar
import kotlinx.coroutines.delay
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
    val showSnackbar = viewModel.showSnackbar.collectAsState().value

    val focusManager = LocalFocusManager.current

    LaunchedEffect(response) {
        response?.let {
            if (it.success) {
                navigateToHome()
            } else {
                viewModel.setShowSnackbar(true)
            }
            viewModel.resetResponse()
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = "KeepSafe",
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Box(modifier = Modifier.padding(top = 24.dp)) {
                        Column(
                            modifier = Modifier
                                .widthIn(max = 460.dp)
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Welcome Back",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                color = MaterialTheme.colorScheme.secondary
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            UsernameTextField(
                                value = username,
                                onValueChange = viewModel::onUsernameValueChange,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        focusManager.moveFocus(FocusDirection.Next)
                                    }
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            )
                            PasswordTextField(
                                value = password,
                                onValueChange = viewModel::onPasswordValueChange,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()
                                    }
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = viewModel::login,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "LOGIN",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }

            if (showSnackbar) {
                Box(
                    modifier = Modifier
                        .widthIn(max = 500.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    GradientSnackBar(
                        message = response?.message ?: "Login failed.",
                        actionLabel = "OK",
                        onAction = { viewModel.setShowSnackbar(false) }
                    )
                    LaunchedEffect(Unit) {
                        delay(3000L)
                        viewModel.setShowSnackbar(false)
                    }
                }
            }
        }
    }
}