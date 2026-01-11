package com.ralphmarondev.keepsafe.features.members.presentation.new_member

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Bloodtype
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Church
import androidx.compose.material.icons.outlined.ContactEmergency
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MedicalInformation
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.PermIdentity
import androidx.compose.material.icons.outlined.PersonPin
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ralphmarondev.keepsafe.R
import com.ralphmarondev.keepsafe.core.common.DateFormatter
import com.ralphmarondev.keepsafe.core.presentation.components.NormalTextField
import com.ralphmarondev.keepsafe.core.presentation.components.PasswordField
import com.ralphmarondev.keepsafe.core.presentation.theme.LocalThemeState
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewMemberScreenRoot(
    navigateBack: () -> Unit
) {
    val viewModel: NewMemberViewModel = koinViewModel()
    val state = viewModel.state.collectAsState().value
    val themeState = LocalThemeState.current

    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            navigateBack()
            viewModel.onAction(action = NewMemberAction.ClearNavigation)
        }
    }

    NewMemberScreen(
        state = state,
        action = viewModel::onAction,
        toggleTheme = themeState::toggleTheme,
        isDarkTheme = themeState.darkTheme.value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewMemberScreen(
    state: NewMemberState,
    action: (NewMemberAction) -> Unit,
    toggleTheme: () -> Unit,
    isDarkTheme: Boolean
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.errorMessage) {
        if (state.isError && !state.errorMessage.isNullOrEmpty()) {
            snackbarHostState.showSnackbar(
                message = state.errorMessage,
                withDismissAction = true
            )
        }
    }

    LaunchedEffect(state.isRegistered) {
        if (state.isRegistered) {
            snackbarHostState.showSnackbar(
                message = "Saved successfully!",
                withDismissAction = true
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
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
                    AnimatedVisibility(visible = state.currentPage >= 1) {
                        IconButton(
                            onClick = { action(NewMemberAction.DecrementCurrentPage) }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBackIosNew,
                                contentDescription = "Navigate Back"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
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
                            0 -> FirstScreen(
                                state = state,
                                action = action
                            )

                            1 -> SecondScreen(
                                state = state,
                                action = action
                            )

                            2 -> ThirdScreen(
                                state = state,
                                action = action
                            )

                            3 -> FourthScreen(
                                state = state,
                                action = action
                            )

                            4 -> AccountScreen(
                                state = state,
                                action = action
                            )

                            5 -> ImageScreen(
                                state = state,
                                action = action
                            )

                            6 -> SummaryScreen(
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


@Composable
private fun FirstScreen(
    state: NewMemberState,
    action: (NewMemberAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Text(
        text = "Let's Get to Know You",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Enter your basic personal details.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.height(12.dp))

    NormalTextField(
        value = state.firstName,
        onValueChange = { action(NewMemberAction.FirstNameChange(it)) },
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
        isError = false,
        supportingText = state.firstNameSupportingText,
        placeHolderText = "Enter first name",
        labelText = "First Name",
        leadingIconImageVector = Icons.Outlined.PermIdentity
    )
    NormalTextField(
        value = state.lastName,
        onValueChange = { action(NewMemberAction.LastNameChange(it)) },
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
        isError = false,
        supportingText = state.lastNameSupportingText,
        placeHolderText = "Enter last name",
        labelText = "Last Name",
        leadingIconImageVector = Icons.Outlined.PermIdentity
    )
    NormalTextField(
        value = state.middleName,
        onValueChange = { action(NewMemberAction.MiddleNameChange(it)) },
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
        isError = false,
        supportingText = state.middleNameSupportingText,
        placeHolderText = "Enter middle name",
        labelText = "Middle Name",
        leadingIconImageVector = Icons.Outlined.PermIdentity
    )
    NormalTextField(
        value = state.maidenName,
        onValueChange = { action(NewMemberAction.MaidenNameChange(it)) },
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
        isError = false,
        supportingText = state.maidenNameSupportingText,
        placeHolderText = "(optional)",
        labelText = "Maiden Name",
        leadingIconImageVector = Icons.Outlined.PermIdentity
    )
    NormalTextField(
        value = state.nickName,
        onValueChange = { action(NewMemberAction.NicknameChange(it)) },
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
        isError = false,
        supportingText = state.nickNameSupportingText,
        placeHolderText = "(optional)",
        labelText = "Nickname",
        leadingIconImageVector = Icons.Outlined.PermIdentity
    )

    Button(
        onClick = { action(NewMemberAction.IncrementCurrentPage) },
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
private fun SecondScreen(
    state: NewMemberState,
    action: (NewMemberAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Text(
        text = "More About You",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Provide additional personal information.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.height(12.dp))

    NormalTextField(
        value = state.civilStatus,
        onValueChange = { action(NewMemberAction.CivilStatusChange(it)) },
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
        isError = false,
        supportingText = state.civilStatusSupportingText,
        placeHolderText = "Enter civil name",
        labelText = "Civil Status",
        leadingIconImageVector = Icons.Outlined.MonitorHeart
    )
    NormalTextField(
        value = state.religion,
        onValueChange = { action(NewMemberAction.ReligionChange(it)) },
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
        isError = false,
        supportingText = state.religionSupportingText,
        placeHolderText = "Enter religion",
        labelText = "Religion",
        leadingIconImageVector = Icons.Outlined.Church
    )
    NormalTextField(
        value = state.gender,
        onValueChange = { action(NewMemberAction.GenderChange(it)) },
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
        isError = false,
        supportingText = state.genderSupportingText,
        placeHolderText = "Enter gender",
        labelText = "Gender",
        leadingIconImageVector = Icons.Outlined.PersonPin
    )
    NormalTextField(
        value = state.birthday,
        onValueChange = { action(NewMemberAction.BirthdayChange(it)) },
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
        isError = false,
        supportingText = state.birthdaySupportingText,
        placeHolderText = "yyyy/mm/dd",
        labelText = "Birthday",
        leadingIconImageVector = Icons.Outlined.Cake
    )
    NormalTextField(
        value = state.birthplace,
        onValueChange = { action(NewMemberAction.BirthplaceChange(it)) },
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
        isError = false,
        supportingText = state.birthplaceSupportingText,
        placeHolderText = "Enter birthplace",
        labelText = "Birthplace",
        leadingIconImageVector = Icons.Outlined.LocationCity
    )

    Button(
        onClick = { action(NewMemberAction.IncrementCurrentPage) },
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
private fun ThirdScreen(
    state: NewMemberState,
    action: (NewMemberAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Text(
        text = "Contact Details",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Share your address and phone number.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.height(12.dp))

    NormalTextField(
        value = state.currentAddress,
        onValueChange = { action(NewMemberAction.CurrentAddressChange(it)) },
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
        isError = false,
        supportingText = state.currentAddressSupportingText,
        placeHolderText = "Enter current address",
        labelText = "Current Address",
        leadingIconImageVector = Icons.Outlined.LocationOn
    )
    NormalTextField(
        value = state.permanentAddress,
        onValueChange = { action(NewMemberAction.PermanentAddressChange(it)) },
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
        isError = false,
        supportingText = state.permanentAddressSupportingText,
        placeHolderText = "Enter permanent address",
        labelText = "Permanent Address",
        leadingIconImageVector = Icons.Outlined.MyLocation
    )
    NormalTextField(
        value = state.phoneNumber,
        onValueChange = { action(NewMemberAction.PhoneNumberChange(it)) },
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
        isError = false,
        supportingText = state.phoneNumberSupportingText,
        placeHolderText = "Enter phone number",
        labelText = "Phone Number",
        leadingIconImageVector = Icons.Outlined.Phone
    )

    Button(
        onClick = { action(NewMemberAction.IncrementCurrentPage) },
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
private fun FourthScreen(
    state: NewMemberState,
    action: (NewMemberAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Text(
        text = "Health Information",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Tell us about your medical details for emergencies.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.height(12.dp))

    NormalTextField(
        value = state.bloodType,
        onValueChange = { action(NewMemberAction.BloodTypeChange(it)) },
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
        isError = false,
        supportingText = state.bloodTypeSupportingText,
        placeHolderText = "A+",
        labelText = "Blood Type",
        leadingIconImageVector = Icons.Outlined.Bloodtype
    )
    NormalTextField(
        value = state.allergies,
        onValueChange = { action(NewMemberAction.AllergiesChange(it)) },
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
        isError = false,
        supportingText = state.allergiesSupportingText,
        placeHolderText = "Enter allergies",
        labelText = "Allergies",
        leadingIconImageVector = Icons.Outlined.Medication
    )
    NormalTextField(
        value = state.medicalConditions,
        onValueChange = { action(NewMemberAction.MedicalConditionsChange(it)) },
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
        isError = false,
        supportingText = state.medicalConditionsSupportingText,
        placeHolderText = "Enter medical conditions",
        labelText = "Medical Conditions",
        leadingIconImageVector = Icons.Outlined.MedicalInformation
    )
    NormalTextField(
        value = state.emergencyContact,
        onValueChange = { action(NewMemberAction.EmergencyContactChange(it)) },
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
        isError = false,
        supportingText = state.emergencyContactSupportingText,
        placeHolderText = "Enter emergency contact",
        labelText = "Emergency Contact",
        leadingIconImageVector = Icons.Outlined.ContactEmergency
    )

    Button(
        onClick = { action(NewMemberAction.IncrementCurrentPage) },
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
private fun AccountScreen(
    state: NewMemberState,
    action: (NewMemberAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Text(
        text = "Almost Done!",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Create your login credentials to complete registration.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.height(12.dp))

    NormalTextField(
        value = state.email,
        onValueChange = { action(NewMemberAction.EmailChange(it)) },
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
        isError = !state.isValidEmail && state.email.isNotEmpty(),
        supportingText = state.emailSupportingText,
        leadingIconImageVector = Icons.Outlined.Email,
        placeHolderText = "you@example.com",
        labelText = "Email"
    )
    PasswordField(
        value = state.password,
        onValueChange = { action(NewMemberAction.PasswordChange(it)) },
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
        isError = !state.isValidPassword && state.password.isNotEmpty(),
        supportingText = state.passwordSupportingText
    )
    PasswordField(
        value = state.confirmPassword,
        onValueChange = { action(NewMemberAction.ConfirmPasswordChange(it)) },
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
        isError = !state.isValidConfirmPassword && state.confirmPassword.isNotEmpty(),
        supportingText = state.confirmPasswordSupportingText
    )

    Button(
        onClick = { action(NewMemberAction.IncrementCurrentPage) },
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
private fun ImageScreen(
    state: NewMemberState,
    action: (NewMemberAction) -> Unit
) {
    val isPreview = LocalInspectionMode.current
    val imageModifier = Modifier
        .size(140.dp)
        .border(1.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(16.dp))
        .clip(RoundedCornerShape(16.dp))
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            action(NewMemberAction.PhotoUrlChange(uri))
        }
    }

    Text(
        text = "You're Almost There!",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Let's wrap things up and get your family registered.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.height(12.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isPreview) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = state.photoUrl,
                contentDescription = null,
                modifier = imageModifier,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                error = painterResource(R.drawable.ic_launcher_foreground),
                contentScale = ContentScale.Crop
            )
        }
        TextButton(
            onClick = {
                imagePickerLauncher.launch("image/*")
            },
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = "Upload Image",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    Button(
        onClick = { action(NewMemberAction.IncrementCurrentPage) },
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
    state: NewMemberState,
    action: (NewMemberAction) -> Unit
) {
    val isPreview = LocalInspectionMode.current
    val imageModifier = Modifier
        .size(80.dp)
        .border(1.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(16.dp))
        .clip(RoundedCornerShape(16.dp))

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

    Spacer(modifier = Modifier.padding(top = 12.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isPreview) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = state.photoUrl,
                contentDescription = null,
                modifier = imageModifier,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                error = painterResource(R.drawable.ic_launcher_foreground),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = "${state.firstName} ${state.lastName}",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Family Member",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    Spacer(modifier = Modifier.height(12.dp))
    InfoOutlinedSection(
        title = "Personal Information",
        items = listOf(
            "First Name: " to state.firstName,
            "Last Name: " to state.lastName,
            "Middle Name: " to state.middleName,
            "Gender: " to state.gender,
            "Civil Status: " to state.civilStatus,
            "Birthdate: " to DateFormatter.format(state.birthday),
            "Current Address: " to state.currentAddress,
            "Permanent Address: " to state.permanentAddress
        )
    )
    Spacer(modifier = Modifier.height(12.dp))

    InfoOutlinedSection(
        title = "Health Information",
        items = listOf(
            "Blood Type: " to state.bloodType,
            "Allergies: " to state.allergies,
            "Condition: " to state.medicalConditions
        )
    )
    Spacer(modifier = Modifier.height(12.dp))
    InfoOutlinedSection(
        title = "Contact Information",
        items = listOf(
            "Phone Number: " to state.phoneNumber,
            "Email: " to state.email
        )
    )

    Button(
        onClick = { action(NewMemberAction.Save) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20),
        enabled = !state.isRegistering
    ) {
        if (state.isRegistering) {
            Text(
                text = "Saving...",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        } else {
            Text(
                text = "Save Family Member",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
}

@Composable
private fun InfoOutlinedSection(
    title: String,
    items: List<Pair<String, String?>>
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        items.forEach { (label, value) ->
            Row(
                modifier = Modifier
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = value ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
