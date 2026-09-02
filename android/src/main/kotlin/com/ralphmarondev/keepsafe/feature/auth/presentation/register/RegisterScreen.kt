package com.ralphmarondev.keepsafe.feature.auth.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.ralphmarondev.keepsafe.R
import com.ralphmarondev.keepsafe.core.presentation.component.KButton
import com.ralphmarondev.keepsafe.core.presentation.component.KLottie
import com.ralphmarondev.keepsafe.core.presentation.component.KOutlinedButton
import com.ralphmarondev.keepsafe.core.presentation.component.KPasswordField
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

    LaunchedEffect(state.isFinished) {
        if (state.isFinished && state.isRegistered) {
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
                title = {
                    Text(
                        text = when (state.page) {
                            RegistrationPage.FamilyInformation -> "Register Family"
                            RegistrationPage.MemberInformation -> "Add First Member"
                            RegistrationPage.AccountInformation -> "Create Account"
                        }
                    )
                },
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
                when (state.page) {
                    RegistrationPage.FamilyInformation -> FamilyInformation(
                        state = state,
                        action = action
                    )

                    RegistrationPage.MemberInformation -> MemberInformation(
                        state = state,
                        action = action
                    )

                    RegistrationPage.AccountInformation -> AccountInformation(
                        state = state,
                        action = action
                    )
                }
            }
        }

        if (state.showDialog) {
            SuccessfulDialog(
                familyCode = state.familyCode,
                onDismissRequest = { action(RegisterAction.DismissDialog) }
            )
        }
    }
}

@Composable
private fun SuccessfulDialog(
    familyCode: String,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
        icon = {
            KLottie(
                animatedResId = R.raw.success,
                size = 120.dp
            )
        },
        title = {
            Text(
                text = "Family Created!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Family Code",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = familyCode,
                        modifier = Modifier.padding(
                            horizontal = 20.dp,
                            vertical = 14.dp
                        ),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(
                    text = "Continue",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    )
}

@Composable
private fun FamilyInformation(
    state: RegisterState,
    action: (RegisterAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column {
        Text(
            text = "Enter your family details to set up your account workspace.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(12.dp))

        KTextField(
            value = state.familyName,
            onValueChange = { action(RegisterAction.FamilyNameChange(it)) },
            modifier = Modifier
                .widthIn(max = 500.dp)
                .fillMaxWidth(),
            label = "Family Name",
            placeholder = "Number One",
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    action(RegisterAction.ChangePage(RegistrationPage.MemberInformation))
                }
            ),
            isError = state.familyNameError,
            supportingText = {
                state.familyNameErrorMessage?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        KButton(
            onClick = { action(RegisterAction.ChangePage(RegistrationPage.MemberInformation)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Proceed",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(12.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        KOutlinedButton(
            onClick = { action(RegisterAction.Login) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Already have an Account",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@Composable
private fun MemberInformation(
    state: RegisterState,
    action: (RegisterAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column {
        Text(
            text = "Please enter the personal details for the primary family account owner.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(12.dp))

        KTextField(
            value = state.firstName,
            onValueChange = { action(RegisterAction.FirstNameChange(it)) },
            modifier = Modifier
                .widthIn(max = 500.dp)
                .fillMaxWidth(),
            label = "First Name",
            placeholder = "Enter first name",
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            isError = state.firstNameError,
            supportingText = {
                state.firstNameErrorMessage?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        )

        KTextField(
            value = state.middleName,
            onValueChange = { action(RegisterAction.MiddleNameChange(it)) },
            modifier = Modifier
                .widthIn(max = 500.dp)
                .fillMaxWidth(),
            label = "Middle Name",
            placeholder = "Enter middle name",
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            isError = state.middleNameError,
            supportingText = {
                state.middleNameErrorMessage?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        )

        KTextField(
            value = state.lastName,
            onValueChange = { action(RegisterAction.LastNameChange(it)) },
            modifier = Modifier
                .widthIn(max = 500.dp)
                .fillMaxWidth(),
            label = "Last Name",
            placeholder = "Enter last name",
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            isError = state.lastNameError,
            supportingText = {
                state.lastNameErrorMessage?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        )

        KTextField(
            value = state.maidenName,
            onValueChange = { action(RegisterAction.MaidenNameChange(it)) },
            modifier = Modifier
                .widthIn(max = 500.dp)
                .fillMaxWidth(),
            label = "Maiden Name",
            placeholder = "Enter maiden name",
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    action(RegisterAction.ChangePage(RegistrationPage.AccountInformation))
                }
            ),
            isError = state.maidenNameError,
            supportingText = {
                state.maidenNameErrorMessage?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        KButton(
            onClick = { action(RegisterAction.ChangePage(RegistrationPage.AccountInformation)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Proceed",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(12.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        KOutlinedButton(
            onClick = { action(RegisterAction.ChangePage(RegistrationPage.FamilyInformation)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Previous",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@Composable
fun AccountInformation(
    state: RegisterState,
    action: (RegisterAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column {
        Text(
            text = "Set up your primary login credentials below.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(12.dp))

        KTextField(
            value = state.username,
            onValueChange = { action(RegisterAction.UsernameChange(it)) },
            modifier = Modifier
                .widthIn(max = 500.dp)
                .fillMaxWidth(),
            label = "Username",
            placeholder = "Enter username",
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            isError = state.usernameError,
            supportingText = {
                state.usernameErrorMessage?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        )

        KPasswordField(
            value = state.password,
            onValueChange = { action(RegisterAction.PasswordChange(it)) },
            modifier = Modifier
                .widthIn(max = 500.dp)
                .fillMaxWidth(),
            label = "Password",
            placeholder = "Enter password",
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            isError = state.passwordError,
            supportingText = {
                state.passwordErrorMessage?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        )

        KPasswordField(
            value = state.confirmPassword,
            onValueChange = { action(RegisterAction.ConfirmPasswordChange(it)) },
            modifier = Modifier
                .widthIn(max = 500.dp)
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            label = "Confirm password",
            placeholder = "Re-enter password",
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    action(RegisterAction.Register)
                }
            ),
            isError = state.confirmPasswordError,
            supportingText = {
                state.confirmPasswordErrorMessage?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        KButton(
            onClick = { action(RegisterAction.Register) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(12.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        KOutlinedButton(
            onClick = { action(RegisterAction.ChangePage(RegistrationPage.MemberInformation)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Previous",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}