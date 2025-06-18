package com.ralphmarondev.keepsafe.settings.presentation.overview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    logout: () -> Unit
) {
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
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Text(
                    text = "App Preferences",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp, horizontal = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
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
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = false,
                        onCheckedChange = {}
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Theme",
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Choose between light and dark themes for the app interface.",
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = {}
                    ) {
                        Text(
                            text = "Light"
                        )
                    }
                }
            }
            item {
                Text(
                    text = "Data Management",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp, horizontal = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
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
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = {},
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Sync",
                            style = MaterialTheme.typography.labelMedium
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
                        .fillMaxWidth()
                        .padding(vertical = 2.dp, horizontal = 16.dp)
                )
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
                            contentDescription = null
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
                            contentDescription = null
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(200.dp))
            }
        }
    }
}