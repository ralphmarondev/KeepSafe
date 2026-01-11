package com.ralphmarondev.keepsafe.core.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun SelectImageButton(
    onClick: (String) -> Unit,
    text: String,
    modifier: Modifier = Modifier
)