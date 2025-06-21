package com.ralphmarondev.keepsafe.settings.presentation.overview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ralphmarondev.keepsafe.settings.presentation.components.ConfirmLogoutDialog
import com.ralphmarondev.keepsafe.settings.presentation.components.SyncWithFirebaseDialog
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    logout: () -> Unit
) {
    val viewModel: SettingViewModel = koinViewModel()
    val showNotification = viewModel.showNotifications.collectAsState().value
    val showSyncWithFirebaseDialog = viewModel.showSyncWithFirebaseDialog.collectAsState().value
    val showConfirmLogoutDialog = viewModel.showConfirmLogoutDialog.collectAsState().value
    val syncResponse = viewModel.syncResponse.collectAsState().value
    val logoutResponse = viewModel.logoutResponse.collectAsState().value

    LaunchedEffect(syncResponse) {
        syncResponse?.let {
            if (it.success) {
                viewModel.setShowSyncWithFirebaseDialog(false)
            }
        }
    }

    LaunchedEffect(logoutResponse) {
        logoutResponse?.let {
            if (it.success) {
                logout()
            }
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = "Settings"
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        titleContentColor = MaterialTheme.colorScheme.secondary
                    )
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Text(
                    text = "App Preferences",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(vertical = 2.dp, horizontal = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Notifications",
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Enable or disable push notifications for reminders and updates.",
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = showNotification,
                        onCheckedChange = { viewModel.toggleShowNotification() }
                    )
                }
            }
            item {
                Text(
                    text = "Data Management",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(vertical = 2.dp, horizontal = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Sync with Firebase",
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Sync your family data with Firebase for backup and cross-device access.",
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = { viewModel.setShowSyncWithFirebaseDialog(true) },
                        modifier = Modifier.height(36.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) {
                        Text(
                            text = "Sync",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Help and Support",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(vertical = 2.dp, horizontal = 16.dp)
                )
                OutlinedCard(
                    onClick = { viewModel.setShowConfirmLogoutDialog(true) },
                    border = BorderStroke(width = 0.dp, color = Color.Transparent)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Logout",
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(200.dp))
            }
        }
    }

    if (showSyncWithFirebaseDialog) {
        SyncWithFirebaseDialog(
            onDismiss = { viewModel.setShowSyncWithFirebaseDialog(false) },
            onConfirm = { viewModel.syncWithFirebase() }
        )
    }

    if (showConfirmLogoutDialog) {
        ConfirmLogoutDialog(
            onDismiss = { viewModel.setShowConfirmLogoutDialog(false) },
            onConfirm = { viewModel.logout() }
        )
    }
}

@Composable
private fun OtherHelpAndSupport() {
    OutlinedCard(
        onClick = {},
        border = BorderStroke(width = 0.dp, color = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Help Center",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
    OutlinedCard(
        onClick = {},
        border = BorderStroke(width = 0.dp, color = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Contact Support",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
    OutlinedCard(
        onClick = {},
        border = BorderStroke(width = 0.dp, color = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Terms of Service",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
    OutlinedCard(
        onClick = {},
        border = BorderStroke(width = 0.dp, color = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Privacy Policy",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}