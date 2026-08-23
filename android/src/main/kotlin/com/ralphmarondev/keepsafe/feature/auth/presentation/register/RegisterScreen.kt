package com.ralphmarondev.keepsafe.feature.auth.presentation.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ralphmarondev.keepsafe.core.presentation.component.KButton
import com.ralphmarondev.keepsafe.core.presentation.component.KOutlinedButton
import com.ralphmarondev.keepsafe.core.presentation.component.KTextField
import com.ralphmarondev.keepsafe.presentation.theme.LocalThemeState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreenRoot(
    onSuccess: () -> Unit,
    onLogin: () -> Unit
) {
    val viewModel: RegisterViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isRegistered) {
        if (state.isRegistered) {
            onSuccess()
        }
    }

    LaunchedEffect(state.login) {
        if (state.login) {
            onLogin()
        }
    }

    RegisterScreen(
        state = state,
        action = viewModel::onAction
    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    action: (RegisterAction) -> Unit
) {
    val themeState = LocalThemeState.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(onClick = themeState::toggleTheme) {
                        val imageVector = when (themeState.darkMode.value) {
                            true -> Icons.Outlined.LightMode
                            false -> Icons.Outlined.DarkMode
                        }
                        Icon(
                            imageVector = imageVector,
                            contentDescription = "Toggle theme"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                AnimatedVisibility(state.page == RegistrationPage.FamilyInformation) {
                    FamilyInformation(
                        state = state,
                        action = action
                    )
                }
            }
        }
    }
}

@Composable
private fun FamilyInformation(
    state: RegisterState,
    action: (RegisterAction) -> Unit
) {
    Column {
        Text(
            text = "Register Family",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Please provide family name.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(8.dp))

        KTextField(
            value = state.familyCode,
            onValueChange = { action(RegisterAction.FamilyCodeChange(it)) },
            modifier = Modifier
                .widthIn(max = 500.dp)
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            label = "Family Code",
            placeholder = "FAM-0001"
        )

        KTextField(
            value = state.familyName,
            onValueChange = { action(RegisterAction.FamilyNameChange(it)) },
            modifier = Modifier
                .widthIn(max = 500.dp)
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            label = "Family Name",
            placeholder = "Number One"
        )

        Spacer(modifier = Modifier.height(16.dp))
        KButton(
            onClick = { action(RegisterAction.ChangePage(RegistrationPage.MemberInformation)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Proceed",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun MemberInformation(
    state: RegisterState,
    action: (RegisterAction) -> Unit
) {
    Column {
        Text(
            text = "Add First Member",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Please provide first member information.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(8.dp))

        KTextField(
            value = state.firstName,
            onValueChange = { action(RegisterAction.FirstNameChange(it)) },
            modifier = Modifier
                .widthIn(max = 500.dp)
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            label = "First Name",
            placeholder = "Enter first name"
        )

        KTextField(
            value = "${state.middleName}",
            onValueChange = { action(RegisterAction.MiddleNameChange(it)) },
            modifier = Modifier
                .widthIn(max = 500.dp)
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            label = "Middle Name",
            placeholder = "Enter middle name"
        )

        KTextField(
            value = state.lastName,
            onValueChange = { action(RegisterAction.LastNameChange(it)) },
            modifier = Modifier
                .widthIn(max = 500.dp)
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            label = "Last Name",
            placeholder = "Enter last name"
        )

        KTextField(
            value = "${state.maidenName}",
            onValueChange = { action(RegisterAction.MaidenNameChange(it)) },
            modifier = Modifier
                .widthIn(max = 500.dp)
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            label = "Maiden Name",
            placeholder = "Enter maiden name"
        )

        Spacer(modifier = Modifier.height(16.dp))
        KButton(
            onClick = { action(RegisterAction.ChangePage(RegistrationPage.AccountInformation)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Proceed",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        KOutlinedButton(
            onClick = { action(RegisterAction.ChangePage(RegistrationPage.FamilyInformation)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Previous",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}