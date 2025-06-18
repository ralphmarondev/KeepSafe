package com.ralphmarondev.keepsafe.family.presentation.new_member

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material.icons.outlined.Password
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ralphmarondev.keepsafe.core.components.GradientSnackBar
import com.ralphmarondev.keepsafe.family.presentation.components.NormalTextField
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun NewFamilyMemberScreen(
    navigateBack: () -> Unit
) {
    val viewModel: NewFamilyMemberViewModel = koinViewModel()
    val fullName = viewModel.fullName.collectAsState().value
    val role = viewModel.role.collectAsState().value
    val birthday = viewModel.birthday.collectAsState().value
    val birthplace = viewModel.birthplace.collectAsState().value
    val email = viewModel.email.collectAsState().value
    val password = viewModel.password.collectAsState().value
    val response = viewModel.response.collectAsState().value

    var showSnackbar by remember { mutableStateOf(false) }

    LaunchedEffect(response) {
        response?.let {
            showSnackbar = !showSnackbar
            if (it.success) {
                delay(1500)
                navigateBack()
            }
        }
    }

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
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                    navigationIconContentColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        NormalTextField(
                            value = fullName,
                            onValueChange = viewModel::onFullNameValueChange,
                            placeholder = "Full name",
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
                            value = email,
                            onValueChange = viewModel::onEmailValueChange,
                            placeholder = "Email",
                            leadingIcon = Icons.Outlined.Email,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                        NormalTextField(
                            value = password,
                            onValueChange = viewModel::onPasswordValueChange,
                            placeholder = "Password",
                            leadingIcon = Icons.Outlined.Password,
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

            AnimatedVisibility(
                visible = showSnackbar,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                GradientSnackBar(
                    message = response?.message ?: "",
                    modifier = Modifier
                        .widthIn(max = 500.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    actionLabel = "OK",
                    onAction = {
                        viewModel.clearResponse()
                        showSnackbar = !showSnackbar
                    }
                )
                LaunchedEffect(Unit) {
                    delay(3000)
                    viewModel.clearResponse()
                    showSnackbar = !showSnackbar
                }
            }
        }
    }
}