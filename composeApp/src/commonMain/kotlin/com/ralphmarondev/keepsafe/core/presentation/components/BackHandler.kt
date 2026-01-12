package com.ralphmarondev.keepsafe.core.presentation.components

import androidx.compose.runtime.Composable

@Composable
expect fun BackHandler(
    enabled: Boolean = false,
    onBack: () -> Unit
)