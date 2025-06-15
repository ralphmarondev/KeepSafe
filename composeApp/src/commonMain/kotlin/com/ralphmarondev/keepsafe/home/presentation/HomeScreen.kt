package com.ralphmarondev.keepsafe.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.vector.ImageVector
import com.ralphmarondev.keepsafe.family.presentation.member_list.FamilyMemberListScreen
import com.ralphmarondev.keepsafe.reminder.presentation.ReminderScreen
import com.ralphmarondev.keepsafe.settings.presentation.overview.SettingScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(
    logout: () -> Unit,
    navigateToNewFamilyMember: () -> Unit,
    navigateToFamilyMemberDetails: (String) -> Unit
) {
    val viewModel: HomeViewModel = koinViewModel()
    val selectedIndex = viewModel.selectedIndex.collectAsState().value
    val role = viewModel.role.collectAsState().value

    val navItems = listOf(
        NavItems(
            onClick = { viewModel.setSelectedIndex(0) },
            selectedIcon = Icons.Filled.Home,
            defaultIcon = Icons.Outlined.Home,
            label = "Home"
        ),
        NavItems(
            onClick = { viewModel.setSelectedIndex(1) },
            selectedIcon = Icons.Filled.CalendarMonth,
            defaultIcon = Icons.Outlined.CalendarMonth,
            label = "Reminders"
        ),
        NavItems(
            onClick = { viewModel.setSelectedIndex(2) },
            selectedIcon = Icons.Filled.Settings,
            defaultIcon = Icons.Outlined.Settings,
            label = "Settings"
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
            AnimatedVisibility(selectedIndex == 0 && role == "Admin") {
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
            0 -> FamilyMemberListScreen(navigateToFamilyMemberDetails)
            1 -> ReminderScreen()
            2 -> SettingScreen(logout = logout)
        }
    }
}

private data class NavItems(
    val onClick: () -> Unit,
    val selectedIcon: ImageVector,
    val defaultIcon: ImageVector,
    val label: String
)