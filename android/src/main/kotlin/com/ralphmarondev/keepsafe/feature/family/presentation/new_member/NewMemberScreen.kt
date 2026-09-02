package com.ralphmarondev.keepsafe.feature.family.presentation.new_member

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ralphmarondev.keepsafe.core.presentation.component.KButton
import com.ralphmarondev.keepsafe.core.presentation.component.KOutlinedButton
import com.ralphmarondev.keepsafe.core.presentation.component.KPasswordField
import com.ralphmarondev.keepsafe.core.presentation.component.KTextField
import com.ralphmarondev.keepsafe.feature.family.domain.enums.MemberRegistrationPage
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NewMemberScreenRoot(
    onSuccess: () -> Unit
) {
    val viewModel: NewMemberViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onSuccess()
        }
    }

    NewMemberScreen(
        state = state,
        action = viewModel::onAction
    )
}

@Composable
private fun NewMemberScreen(
    state: NewMemberState,
    action: (NewMemberAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "New Member")
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
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                when (state.page) {
                    MemberRegistrationPage.BasicInformation -> BasicInformation(
                        state = state,
                        action = action
                    )

                    MemberRegistrationPage.MoreInformation -> MoreInformation(
                        state = state,
                        action = action
                    )

                    MemberRegistrationPage.AccountInformation -> AccountInformation(
                        state = state,
                        action = action
                    )
                }
            }
        }
    }
}

@Composable
private fun BasicInformation(
    state: NewMemberState,
    action: (NewMemberAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column {
        Text(
            text = "Basic Information",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Please provide first member information.",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(12.dp))

        KTextField(
            value = state.firstName,
            onValueChange = { action(NewMemberAction.FirstNameChange(it)) },
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
            onValueChange = { action(NewMemberAction.MiddleNameChange(it)) },
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
            onValueChange = { action(NewMemberAction.LastNameChange(it)) },
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
            onValueChange = { action(NewMemberAction.MaidenNameChange(it)) },
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
                    action(NewMemberAction.ChangePage(MemberRegistrationPage.MoreInformation))
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
            onClick = { action(NewMemberAction.ChangePage(MemberRegistrationPage.MoreInformation)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Proceed",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}


@Composable
private fun MoreInformation(
    state: NewMemberState,
    action: (NewMemberAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column {
        Text(
            text = "More Information",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Please provide first member information.",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(12.dp))

        KTextField(
            value = state.contactNumber,
            onValueChange = { action(NewMemberAction.ContactNumberChange(it)) },
            modifier = Modifier
                .widthIn(max = 500.dp)
                .fillMaxWidth(),
            label = "Contact Number",
            placeholder = "09123456789",
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Phone
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            isError = state.contactNumberError,
            supportingText = {
                state.contactNumberErrorMessage?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        )

        KTextField(
            value = state.birthday,
            onValueChange = { action(NewMemberAction.BirthdayChange(it)) },
            modifier = Modifier
                .widthIn(max = 500.dp)
                .fillMaxWidth(),
            label = "Birthday",
            placeholder = "Jan 1, 2026",
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    action(NewMemberAction.Save)
                }
            ),
            isError = state.birthdayError,
            supportingText = {
                state.birthdayErrorMessage?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        KButton(
            onClick = { action(NewMemberAction.ChangePage(MemberRegistrationPage.AccountInformation)) },
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
            onClick = { action(NewMemberAction.ChangePage(MemberRegistrationPage.BasicInformation)) },
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
    state: NewMemberState,
    action: (NewMemberAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column {
        Text(
            text = "Create an account",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "This is used for authentication.",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(12.dp))

        KTextField(
            value = state.username,
            onValueChange = { action(NewMemberAction.UsernameChange(it)) },
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
            onValueChange = { action(NewMemberAction.PasswordChange(it)) },
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
            onValueChange = { action(NewMemberAction.ConfirmPasswordChange(it)) },
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
                    action(NewMemberAction.Save)
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
            onClick = { action(NewMemberAction.Save) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Save",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(12.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        KOutlinedButton(
            onClick = { action(NewMemberAction.ChangePage(MemberRegistrationPage.MoreInformation)) },
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