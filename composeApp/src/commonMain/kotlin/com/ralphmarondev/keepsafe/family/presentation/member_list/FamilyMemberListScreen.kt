package com.ralphmarondev.keepsafe.family.presentation.member_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Refresh
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ralphmarondev.keepsafe.core.theme.LocalThemeState
import com.ralphmarondev.keepsafe.core.util.isDesktop
import com.ralphmarondev.keepsafe.family.presentation.components.FamilyMemberCard
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
@Composable
fun FamilyMemberListScreen(
    navigateToFamilyMemberDetails: (String) -> Unit
) {
    val viewModel: FamilyMemberListViewModel = koinViewModel()
    val familyMember = viewModel.familyMember.collectAsState().value
    val isRefreshing = viewModel.isRefreshing.collectAsState().value
    val response = viewModel.response.collectAsState().value

    val themeState = LocalThemeState.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Home"
                    )
                },
                actions = {
                    AnimatedVisibility(
                        visible = isDesktop()
                    ) {
                        IconButton(onClick = viewModel::refresh) {
                            Icon(
                                imageVector = Icons.Outlined.Refresh,
                                contentDescription = "Refresh"
                            )
                        }
                    }

                    IconButton(onClick = themeState::toggleTheme) {
                        val imageVector = if (themeState.darkMode.value) {
                            Icons.Outlined.LightMode
                        } else {
                            Icons.Outlined.DarkMode
                        }
                        Icon(
                            imageVector = imageVector,
                            contentDescription = "Toggle theme"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = viewModel::refresh,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = if (response?.success == false) {
                    Arrangement.Center
                } else {
                    Arrangement.Top
                },
                contentPadding = PaddingValues(16.dp)
            ) {
                items(familyMember) {
                    FamilyMemberCard(
                        modifier = Modifier
                            .widthIn(max = 500.dp)
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        familyMember = it,
                        onClick = {
                            navigateToFamilyMemberDetails(it.uid)
                        }
                    )
                }
                item {
                    AnimatedVisibility(
                        visible = response?.success == false
                    ) {
                        Text(
                            text = response?.message ?: "An error occurred.",
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                item { Spacer(modifier = Modifier.height(100.dp)) }
            }
        }
    }
}