package com.ralphmarondev.keepsafe.family.presentation.member_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Update
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import com.ralphmarondev.keepsafe.family.presentation.components.DeleteFamilyMemberDialog
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun FamilyMemberDetailScreen(
    memberId: String,
    navigateBack: () -> Unit,
    navigateToUpdate: (String) -> Unit
) {
    val viewModel: FamilyMemberDetailViewModel = koinViewModel(
        parameters = { parametersOf(memberId) }
    )
    val details = viewModel.detail.collectAsState().value
    val role = viewModel.role.collectAsState().value
    val showDeleteDialog = viewModel.showDeleteDialog.collectAsState().value
    val response = viewModel.response.collectAsState().value

    LaunchedEffect(response) {
        response?.let {
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
                        text = "Details"
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
                actions = {
                    val isAdmin = role.isNotBlank() && role == "Admin"

                    AnimatedVisibility(visible = isAdmin) {
                        IconButton(
                            onClick = {
                                navigateToUpdate(memberId)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Update,
                                contentDescription = "Update"
                            )
                        }
                    }
                    AnimatedVisibility(visible = isAdmin) {
                        IconButton(onClick = { viewModel.setOnDeleteDialog(true) }) {
                            Icon(
                                imageVector = Icons.Outlined.DeleteOutline,
                                contentDescription = "Delete"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                    navigationIconContentColor = MaterialTheme.colorScheme.secondary,
                    actionIconContentColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Full Name:",
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = details?.fullName ?: "No full name provided",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Email:",
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = details?.email ?: "No email provided",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Birthday:",
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = details?.birthday ?: "No birthday provided",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Birthplace:",
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = details?.birthplace ?: "No birthplace provided",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }

    if (showDeleteDialog) {
        DeleteFamilyMemberDialog(
            onDismiss = { viewModel.setOnDeleteDialog(false) },
            onConfirm = viewModel::delete
        )
    }
}