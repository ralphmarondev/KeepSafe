package com.ralphmarondev.keepsafe.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import keepsafe.composeapp.generated.resources.Res

@Composable
fun LottieAnimation(
    animation: String,
    modifier: Modifier = Modifier,
    iterations: Int = Compottie.IterateForever
) {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/$animation").decodeToString()
        )
    }

    Image(
        painter = rememberLottiePainter(
            composition = composition,
            iterations = iterations
        ),
        contentDescription = null,
        modifier = modifier
    )
}