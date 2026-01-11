package com.ralphmarondev.keepsafe.features.auth.presentation.register

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountTree
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.PermIdentity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ralphmarondev.keepsafe.core.presentation.components.NormalTextField
import com.ralphmarondev.keepsafe.core.presentation.components.PasswordField
import com.ralphmarondev.keepsafe.core.presentation.theme.LocalThemeState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreenRoot(
    navigateToLogin: () -> Unit
) {
    val viewModel: RegisterViewModel = koinViewModel()
    val state = viewModel.state.collectAsState().value
    val themeState = LocalThemeState.current

    LaunchedEffect(state.navigateToLogin) {
        if (state.navigateToLogin) {
            navigateToLogin()
            viewModel.onAction(action = RegisterAction.ClearNavigation)
        }
    }

    RegisterScreen(
        state = state,
        action = viewModel::onAction,
        toggleTheme = themeState::toggleTheme,
        isDarkTheme = themeState.darkTheme.value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterScreen(
    state: RegisterState,
    action: (RegisterAction) -> Unit,
    toggleTheme: () -> Unit,
    isDarkTheme: Boolean
) {
    BackHandler(enabled = true) {
        if (state.currentPage > 0) {
            action(RegisterAction.DecrementCurrentPage)
        } else {
            action(RegisterAction.ShowNavigateBackDialog)
        }
    }

    val snackbarState = remember { SnackbarHostState() }

    LaunchedEffect(state.errorMessage) {
        if (state.isError && !state.errorMessage.isNullOrEmpty()) {
            snackbarState.showSnackbar(message = state.errorMessage)
        }
    }

    LaunchedEffect(state.isRegistered) {
        if (state.isRegistered) {
            snackbarState.showSnackbar(message = "Family Profile Created Successfully!")
        }
    }

    AnimatedVisibility(visible = state.showNavigateBackDialog) {
        AlertDialog(
            onDismissRequest = { action(RegisterAction.CloseNavigateBackDialog) },
            confirmButton = {
                TextButton(
                    onClick = {
                        action(RegisterAction.CloseNavigateBackDialog)
                        action(RegisterAction.NavigateToLogin)
                    }
                ) {
                    Text(
                        text = "Yes",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { action(RegisterAction.CloseNavigateBackDialog) }) {
                    Text(
                        text = "No",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            },
            title = {
                Text(
                    text = "Navigate to Login?",
                    color = MaterialTheme.colorScheme.secondary
                )
            },
            text = {
                Text(
                    text = "Your changes won't be saved. Do you want to continue?",
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(
                        onClick = toggleTheme
                    ) {
                        val imageVector = if (isDarkTheme) {
                            Icons.Outlined.LightMode
                        } else {
                            Icons.Outlined.DarkMode
                        }
                        val contentDescription = if (isDarkTheme) {
                            "Switch to light mode"
                        } else {
                            "Switch to dark mode"
                        }
                        Icon(
                            imageVector = imageVector,
                            contentDescription = contentDescription
                        )
                    }
                },
                navigationIcon = {
                    AnimatedVisibility(state.currentPage > 0) {
                        IconButton(
                            onClick = { action(RegisterAction.DecrementCurrentPage) }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBackIosNew,
                                contentDescription = "Previous"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    actionIconContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                OutlinedCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        when (state.currentPage) {
                            0 -> {
                                RegisterFamilyProfileScreen(
                                    state = state,
                                    action = action
                                )
                            }

                            1 -> {
                                RegisterAdminAccountScreen(
                                    state = state,
                                    action = action
                                )
                            }

                            2 -> {
                                SummaryScreen(
                                    state = state,
                                    action = action
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RegisterFamilyProfileScreen(
    state: RegisterState,
    action: (RegisterAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Text(
        text = "Create Your Family Profile",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Start by adding your family name and ID.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.height(12.dp))

    NormalTextField(
        value = state.familyName,
        onValueChange = { action(RegisterAction.FamilyNameChange(it)) },
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            }
        ),
        isError = !state.isValidFamilyName,
        supportingText = state.familyNameSupportingText,
        leadingIconImageVector = Icons.Outlined.AccountTree,
        labelText = "Family Name",
        placeHolderText = "Your Family"
    )

    NormalTextField(
        value = state.familyId,
        onValueChange = { action(RegisterAction.FamilyIdChange(it)) },
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        isError = !state.isValidFamilyId,
        supportingText = state.familyIdSupportingText,
        leadingIconImageVector = Icons.Outlined.AccountTree,
        labelText = "Family ID",
        placeHolderText = "your-family"
    )

    Button(
        onClick = { action(RegisterAction.IncrementCurrentPage) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20),
    ) {
        Text(
            text = "Next",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 2.dp)
        )
    }
    Button(
        onClick = { action(RegisterAction.NavigateToLogin) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Text(
            text = "Already Have a Family",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 2.dp)
        )
    }
}

@Composable
private fun RegisterAdminAccountScreen(
    state: RegisterState,
    action: (RegisterAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Text(
        text = "Let's Meet the Family Admin",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Set up your admin profile and take charge!",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.height(12.dp))

    NormalTextField(
        value = state.username,
        onValueChange = { action(RegisterAction.UsernameChange(it)) },
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            }
        ),
        isError = !state.isValidUsername,
        supportingText = state.usernameSupportingText,
        placeHolderText = "admin",
        labelText = "Username",
        leadingIconImageVector = Icons.Outlined.PermIdentity
    )
    PasswordField(
        value = state.password,
        onValueChange = { action(RegisterAction.PasswordChange(it)) },
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.moveFocus(FocusDirection.Next)
            }
        ),
        isError = !state.isValidPassword,
        supportingText = state.passwordSupportingText,
        labelText = "Password"
    )
    PasswordField(
        value = state.confirmPassword,
        onValueChange = { action(RegisterAction.ConfirmPasswordChange(it)) },
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        labelText = "Confirm Password",
        isError = !state.isValidConfirmPassword,
        supportingText = state.confirmPasswordSupportingText
    )

    Button(
        onClick = { action(RegisterAction.IncrementCurrentPage) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20)
    ) {
        Text(
            text = "Next",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 2.dp)
        )
    }
}

@Composable
private fun SummaryScreen(
    state: RegisterState,
    action: (RegisterAction) -> Unit
) {
    Text(
        text = "Review Your Details",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Make sure everything is correct before registering.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )

    Spacer(modifier = Modifier.width(16.dp))
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "Family ID:",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = state.familyId,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.primary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "Family Name:",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = state.familyName,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.primary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "Admin Username:",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = state.email,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.primary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }

    Spacer(modifier = Modifier.height(12.dp))
    Button(
        onClick = { action(RegisterAction.Register) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20),
        enabled = !state.isRegistering
    ) {
        if (state.isRegistering) {
            Text(
                text = "Registering...",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        } else {
            Text(
                text = "Register",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
}