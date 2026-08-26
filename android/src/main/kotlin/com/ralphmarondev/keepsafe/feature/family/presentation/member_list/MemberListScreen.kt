package com.ralphmarondev.keepsafe.feature.family.presentation.member_list

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ralphmarondev.keepsafe.feature.family.presentation.component.MemberCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MemberListScreenRoot(
    profile: () -> Unit,
    newMember: () -> Unit,
    memberDetail: (String) -> Unit
) {
    val viewModel: MemberListViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onAction(MemberListAction.LoadMembers)
    }

    LaunchedEffect(state.navigateToNewMember) {
        if (state.navigateToNewMember) {
            newMember()
            viewModel.onAction(MemberListAction.ClearNavigation)
        }
    }

    LaunchedEffect(state.navigateToMemberDetail) {
        if (state.navigateToMemberDetail && state.selectedMember.uid.isNotBlank()) {
            memberDetail(state.selectedMember.uid)
            viewModel.onAction(MemberListAction.ClearNavigation)
        }
    }

    LaunchedEffect(state.navigateToProfile) {
        if (state.navigateToProfile) {
            profile()
            viewModel.onAction(MemberListAction.ClearNavigation)
        }
    }

    MemberListScreen(
        state = state,
        action = viewModel::onAction
    )
}

@Composable
private fun MemberListScreen(
    state: MemberListState,
    action: (MemberListAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AnimatedVisibility(state.family.name.isNotBlank()) {
                        Text(text = "${state.family.name} Family")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { action(MemberListAction.NavigateToProfile) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { action(MemberListAction.NewMember) }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add New Member"
                )
            }
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { action(MemberListAction.Refresh) },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(state.members, key = { it.uid }) { member ->
                    MemberCard(
                        member = member,
                        modifier = Modifier
                            .widthIn(max = 500.dp)
                            .fillMaxWidth(),
                        onClick = { action(MemberListAction.MemberSelected(member)) }
                    )
                }
                item { Spacer(modifier = Modifier.height(100.dp)) }
            }
        }
    }
}