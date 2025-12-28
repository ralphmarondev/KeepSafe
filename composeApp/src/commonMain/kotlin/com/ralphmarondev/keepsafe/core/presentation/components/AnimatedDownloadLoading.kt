package com.ralphmarondev.keepsafe.core.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun AnimatedDownloadLoading(
    isDownloadCompleted: Boolean,
    modifier: Modifier = Modifier
)