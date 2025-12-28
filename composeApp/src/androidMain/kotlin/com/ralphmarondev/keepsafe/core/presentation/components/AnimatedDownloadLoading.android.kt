package com.ralphmarondev.keepsafe.core.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ralphmarondev.keepsafe.R

@Composable
actual fun AnimatedDownloadLoading(
    isDownloadCompleted: Boolean,
    modifier: Modifier
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            resId = when (isDownloadCompleted) {
                true -> R.raw.all_set
                false -> R.raw.downloading
            }
        )
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier
    )
}