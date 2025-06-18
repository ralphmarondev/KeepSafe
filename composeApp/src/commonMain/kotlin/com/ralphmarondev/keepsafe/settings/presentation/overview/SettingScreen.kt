package com.ralphmarondev.keepsafe.settings.presentation.overview

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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.MiscellaneousServices
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ralphmarondev.keepsafe.settings.presentation.components.SettingCard
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    logout: () -> Unit
) {
    val viewModel: SettingViewModel = koinViewModel()
    val fullName = viewModel.fullName.collectAsState().value

    val personalInformationOptions = listOf(
        SettingOptions(
            leadingIcon = Icons.Outlined.AccountBox,
            label = "Full Name",
            onClick = {}
        ),
        SettingOptions(
            leadingIcon = Icons.Outlined.Cake,
            label = "Birthday",
            onClick = {}
        ),
        SettingOptions(
            leadingIcon = Icons.Outlined.Map,
            label = "Birthplace",
            onClick = {}
        ),
        SettingOptions(
            leadingIcon = Icons.Outlined.Email,
            label = "Email",
            onClick = {}
        ),
        SettingOptions(
            leadingIcon = Icons.Outlined.Password,
            label = "Password",
            onClick = {}
        )
    )
    val appPreferencesOptions = listOf(
        SettingOptions(
            leadingIcon = Icons.Outlined.LightMode,
            label = "Theme",
            onClick = {}
        ),
        SettingOptions(
            leadingIcon = Icons.Outlined.Notifications,
            label = "Notifications",
            onClick = {}
        ),
        SettingOptions(
            leadingIcon = Icons.Outlined.Language,
            label = "Language",
            onClick = {}
        )
    )
    val aboutOptions = listOf(
        SettingOptions(
            leadingIcon = Icons.Outlined.Info,
            label = "Version",
            onClick = {}
        ),
        SettingOptions(
            leadingIcon = Icons.Outlined.MiscellaneousServices,
            label = "Terms of Service",
            onClick = {}
        ),
        SettingOptions(
            leadingIcon = Icons.AutoMirrored.Outlined.Logout,
            label = "Logout",
            onClick = { logout() }
        )
    )

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
                    text = "Personal Information",
                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
                    fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .widthIn(max = 500.dp)
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                )
            }
            items(personalInformationOptions) {
                SettingCard(
                    modifier = Modifier
                        .widthIn(max = 500.dp)
                        .fillMaxWidth(),
                    onClick = it.onClick,
                    label = it.label,
                    leadingIcon = it.leadingIcon
                )
            }
            item {
                Text(
                    text = "App Preferences",
                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
                    fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .widthIn(max = 500.dp)
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                )
            }
            items(appPreferencesOptions) {
                SettingCard(
                    modifier = Modifier
                        .widthIn(max = 500.dp)
                        .fillMaxWidth(),
                    onClick = it.onClick,
                    label = it.label,
                    leadingIcon = it.leadingIcon
                )
            }
            item {
                Text(
                    text = "About",
                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
                    fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .widthIn(max = 500.dp)
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                )
            }
            items(aboutOptions) {
                SettingCard(
                    modifier = Modifier
                        .widthIn(max = 500.dp)
                        .fillMaxWidth(),
                    onClick = it.onClick,
                    label = it.label,
                    leadingIcon = it.leadingIcon
                )
            }
            item {
                Spacer(modifier = Modifier.height(200.dp))
            }
        }
    }
}

private data class SettingOptions(
    val leadingIcon: ImageVector,
    val label: String,
    val onClick: () -> Unit
)