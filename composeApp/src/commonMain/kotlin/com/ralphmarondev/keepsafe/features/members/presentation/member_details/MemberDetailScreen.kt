package com.ralphmarondev.keepsafe.features.members.presentation.member_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ralphmarondev.keepsafe.core.presentation.theme.LocalThemeState
import keepsafe.composeapp.generated.resources.Res
import keepsafe.composeapp.generated.resources.ic_launcher_foreground
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MemberDetailScreenRoot(
    email: String,
    navigateBack: () -> Unit
) {
    val viewModel: MemberDetailViewModel = koinViewModel { parametersOf(email) }
    val state = viewModel.state.collectAsState().value
    val themeState = LocalThemeState.current

    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            navigateBack()
            viewModel.onAction(MemberDetailAction.ClearNavigation)
        }
    }

    MemberDetailScreen(
        state = state,
        action = viewModel::onAction,
        toggleTheme = themeState::toggleTheme,
        isDarkTheme = themeState.darkTheme.value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MemberDetailScreen(
    state: MemberDetailState,
    action: (MemberDetailAction) -> Unit,
    toggleTheme: () -> Unit,
    isDarkTheme: Boolean
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Details"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { action(MemberDetailAction.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                actions = {
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
                    actionIconContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                MemberHeader(
                    name = "${state.member.firstName} ${state.member.lastName}",
                    role = state.member.role.replace("_", " "),
                    photoPath = state.member.photoUrl ?: ""
                )
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
            item {
                InfoOutlinedSection(
                    title = "Personal Information",
                    items = listOf(
                        "First Name: " to state.member.firstName,
                        "Last Name: " to state.member.lastName,
                        "Middle Name: " to state.member.middleName,
                        "Gender: " to state.member.gender,
                        "Civil Status: " to state.member.civilStatus,
                        "Birthdate: " to state.member.birthday, // DateFormatter.format(state.member.birthday),
                        "Current Address: " to state.member.currentAddress,
                        "Permanent Address: " to state.member.permanentAddress
                    )
                )
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
            item {
                InfoOutlinedSection(
                    title = "Health Information",
                    items = listOf(
                        "Blood Type: " to state.member.bloodType,
                        "Allergies: " to state.member.allergies,
                        "Condition: " to state.member.medicalConditions
                    )
                )
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
            item {
                InfoOutlinedSection(
                    title = "Contact Information",
                    items = listOf(
                        "Phone Number: " to state.member.phoneNumber,
                        "Email: " to state.member.email
                    )
                )
            }
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

@Composable
private fun MemberHeader(
    name: String,
    role: String,
    photoPath: String
) {
    val isPreview = LocalInspectionMode.current
    val imageModifier = Modifier
        .size(140.dp)
        .border(1.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(16.dp))
        .clip(RoundedCornerShape(16.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isPreview) {
            Image(
                painter = painterResource(Res.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = photoPath,
                contentDescription = null,
                modifier = imageModifier,
                placeholder = painterResource(Res.drawable.ic_launcher_foreground),
                error = painterResource(Res.drawable.ic_launcher_foreground),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = role,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(20),
            ) {
                Text(
                    text = "Call Me",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun InfoOutlinedSection(
    title: String,
    items: List<Pair<String, String?>>
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            items.filter { !it.second.isNullOrBlank() }.forEach { (label, value) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = value ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}