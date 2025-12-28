package com.ralphmarondev.keepsafe.core.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
actual fun AnimatedDownloadLoading(
    isDownloadCompleted: Boolean,
    modifier: Modifier
) {
    Canvas(modifier = modifier.background(Color.LightGray)) {
        drawCircle(
            color = Color.Blue,
            center = center,
            radius = size.minDimension
        )
    }
}