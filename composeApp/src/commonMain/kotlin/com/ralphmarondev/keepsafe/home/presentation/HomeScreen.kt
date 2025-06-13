package com.ralphmarondev.keepsafe.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ralphmarondev.keepsafe.family_list.presentation.FamilyListScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(
    logout: () -> Unit,
    navigateToNewFamilyMember: () -> Unit
) {
    val viewModel: HomeViewModel = koinViewModel()
    val selectedIndex = viewModel.selectedIndex.collectAsState().value

    val navItems = listOf(
        NavItems(
            onClick = { viewModel.setSelectedIndex(0) },
            selectedIcon = Icons.Filled.Home,
            defaultIcon = Icons.Outlined.Home,
            label = "Home"
        ),
        NavItems(
            onClick = { viewModel.setSelectedIndex(1) },
            selectedIcon = Icons.Filled.Settings,
            defaultIcon = Icons.Outlined.Settings,
            label = "Settings"
        ),
        NavItems(
            onClick = { viewModel.setSelectedIndex(2) },
            selectedIcon = Icons.Filled.AccountCircle,
            defaultIcon = Icons.Outlined.AccountCircle,
            label = "Profile"
        )
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                navItems.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        icon = {
                            val imageVector = if (selectedIndex == index) {
                                navItem.selectedIcon
                            } else {
                                navItem.defaultIcon
                            }
                            Icon(
                                imageVector = imageVector,
                                contentDescription = navItem.label
                            )
                        },
                        label = {
                            Text(
                                text = navItem.label
                            )
                        },
                        onClick = navItem.onClick
                    )
                }
            }
        },
        floatingActionButton = {
            AnimatedVisibility(selectedIndex == 0) {
                FloatingActionButton(onClick = navigateToNewFamilyMember) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "New family member"
                    )
                }
            }
        }
    ) {
        when (selectedIndex) {
            0 -> FamilyListScreen()

            1 -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Settings",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = logout
                    ) {
                        Text(
                            text = "LOGOUT"
                        )
                    }
                }
            }

            2 -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Profile",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

private data class NavItems(
    val onClick: () -> Unit,
    val selectedIcon: ImageVector,
    val defaultIcon: ImageVector,
    val label: String
)