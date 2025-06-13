package com.ralphmarondev.keepsafe.new_family_member.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ralphmarondev.keepsafe.new_family_member.presentation.components.NormalTextField
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun NewFamilyMemberScreen(
    navigateBack: () -> Unit
) {
    val viewModel: NewFamilyMemberViewModel = koinViewModel()
    val firstName = viewModel.firstName.collectAsState().value
    val lastName = viewModel.lastName.collectAsState().value
    val middleName = viewModel.middleName.collectAsState().value
    val role = viewModel.role.collectAsState().value
    val birthday = viewModel.birthday.collectAsState().value
    val birthplace = viewModel.birthplace.collectAsState().value
    val email = viewModel.email.collectAsState().value
    val phoneNumber = viewModel.phoneNumber.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "New Family Member"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    modifier = Modifier
                        .width(500.dp)
                        .fillMaxWidth()
                ) {
                    NormalTextField(
                        value = firstName,
                        onValueChange = viewModel::onFirstNameValueChange,
                        placeholder = "First name",
                        leadingIcon = Icons.Outlined.AccountBox,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    NormalTextField(
                        value = middleName,
                        onValueChange = viewModel::onMiddleNameValueChange,
                        placeholder = "Middle name",
                        leadingIcon = Icons.Outlined.AccountBox,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    NormalTextField(
                        value = lastName,
                        onValueChange = viewModel::onLastNameValueChange,
                        placeholder = "Last name",
                        leadingIcon = Icons.Outlined.AccountBox,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    NormalTextField(
                        value = role,
                        onValueChange = viewModel::onRoleValueChange,
                        placeholder = "Role",
                        leadingIcon = Icons.Outlined.Grade,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    NormalTextField(
                        value = birthday,
                        onValueChange = viewModel::onBirthdayValueChange,
                        placeholder = "Birthday",
                        leadingIcon = Icons.Outlined.Cake,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    NormalTextField(
                        value = birthplace,
                        onValueChange = viewModel::onBirthplaceValueChange,
                        placeholder = "Birthplace",
                        leadingIcon = Icons.Outlined.Place,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    NormalTextField(
                        value = phoneNumber,
                        onValueChange = viewModel::onPhoneNumberValueChange,
                        placeholder = "Phone number",
                        leadingIcon = Icons.Outlined.Phone,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    NormalTextField(
                        value = email,
                        onValueChange = viewModel::onEmailValueChange,
                        placeholder = "Email",
                        leadingIcon = Icons.Outlined.Email,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = viewModel::register,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "REGISTER",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                        )
                    }
                }
            }
        }
    }
}