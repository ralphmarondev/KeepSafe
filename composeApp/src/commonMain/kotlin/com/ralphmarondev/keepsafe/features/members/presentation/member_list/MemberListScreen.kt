package com.ralphmarondev.keepsafe.features.members.presentation.member_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ralphmarondev.keepsafe.core.presentation.theme.LocalThemeState
import com.ralphmarondev.keepsafe.features.members.presentation.components.AddMemberCard
import com.ralphmarondev.keepsafe.features.members.presentation.components.MemberCard
import keepsafe.composeapp.generated.resources.Res
import keepsafe.composeapp.generated.resources.ic_launcher_foreground
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

typealias Email = String

@Composable
fun MemberListScreenRoot(
    onFamilyMemberClick: (Email) -> Unit,
    onAccountClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onAddNewFamilyMemberClick: () -> Unit
) {
    val viewModel: MemberListViewModel = koinViewModel()
    val state = viewModel.state.collectAsState().value
    val themeState = LocalThemeState.current

    LaunchedEffect(state.navigateToDetails) {
        if (state.navigateToDetails) {
            onFamilyMemberClick(state.selectedMemberEmail)
            viewModel.onAction(MemberListAction.ClearNavigation)
        }
    }

    LaunchedEffect(state.navigateToAccount) {
        if (state.navigateToAccount) {
            onAccountClick()
            viewModel.onAction(MemberListAction.ClearNavigation)
        }
    }

    LaunchedEffect(state.navigateToNotification) {
        if (state.navigateToNotification) {
            onNotificationClick()
            viewModel.onAction(MemberListAction.ClearNavigation)
        }
    }

    LaunchedEffect(state.navigateToNewFamilyMember) {
        if (state.navigateToNewFamilyMember) {
            onAddNewFamilyMemberClick()
            viewModel.onAction(MemberListAction.ClearNavigation)
        }
    }

    MemberListScreen(
        state = state,
        action = viewModel::onAction,
        toggleTheme = themeState::toggleTheme,
        isDarkTheme = themeState.darkTheme.value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MemberListScreen(
    state: MemberListState,
    action: (MemberListAction) -> Unit,
    toggleTheme: () -> Unit,
    isDarkTheme: Boolean
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Family"
                    )
                },
                actions = {
                    IconButton(onClick = { action(MemberListAction.NavigateToNotification) }) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notification"
                        )
                    }
                    IconButton(onClick = { action(MemberListAction.NavigateToAccount) }) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = "Account"
                        )
                    }
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
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            isRefreshing = state.isRefreshing,
            onRefresh = { action(MemberListAction.Refresh) }
        ) {
            when (state.members.isEmpty() && !state.isLoading) {
                true -> {
                    EmptyMemberList(
                        state = state,
                        action = action
                    )
                }

                false -> {
                    MemberListContent(
                        state = state,
                        action = action
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyMemberList(
    state: MemberListState,
    action: (MemberListAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_launcher_foreground),
            contentDescription = "No family members yet",
            modifier = Modifier
                .size(200.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Your family is looking a bit empty!",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (state.isCurrentUserAdmin) {
                "Add your first family member to get started."
            } else {
                "Only admins can add family members. Ask an admin to invite someone."
            },
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.tertiary
        )

        Spacer(modifier = Modifier.height(24.dp))

        AnimatedVisibility(visible = state.isCurrentUserAdmin) {
            Button(
                onClick = { action(MemberListAction.NavigateToNewFamilyMember) },
                modifier = Modifier
                    .padding(8.dp),
                shape = RoundedCornerShape(20)
            ) {
                Text(
                    text = "Add Family Member",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun MemberListContent(
    state: MemberListState,
    action: (MemberListAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            AnimatedVisibility(visible = state.isCurrentUserAdmin) {
                AddMemberCard(
                    onClick = { action(MemberListAction.NavigateToNewFamilyMember) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
        items(items = state.members) { member ->
            MemberCard(
                member = member,
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { action(MemberListAction.NavigateToDetails(member.email)) }
            )
        }
        item { Spacer(modifier = Modifier.height(100.dp)) }
    }
}