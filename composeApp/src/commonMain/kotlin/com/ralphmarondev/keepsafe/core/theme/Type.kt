package com.ralphmarondev.keepsafe.core.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import keepsafe.composeapp.generated.resources.Res
import keepsafe.composeapp.generated.resources.roboto_mono_bold
import keepsafe.composeapp.generated.resources.roboto_mono_regular
import keepsafe.composeapp.generated.resources.roboto_mono_thin
import org.jetbrains.compose.resources.Font

// 2025-06-04
// NOTE: THIS IS NOT WORKING LOL
// 2025-06-23
// NOTE: IT'S WORKING NOW LOL

val RobotoMonoBold
    @Composable get() = FontFamily(
        Font(
            resource = Res.font.roboto_mono_bold
        )
    )

val RobotoMonoRegular
    @Composable get() = FontFamily(
        Font(
            resource = Res.font.roboto_mono_regular
        )
    )

val RobotoMonoThin
    @Composable get() = FontFamily(
        Font(
            resource = Res.font.roboto_mono_thin
        )
    )

val Typography: Typography
    @Composable get() = Typography(
        titleLarge = TextStyle(
            fontFamily = RobotoMonoBold,
            fontSize = 22.sp
        ),
        titleMedium = TextStyle(
            fontFamily = RobotoMonoRegular,
            fontSize = 18.sp
        ),
        titleSmall = TextStyle(
            fontFamily = RobotoMonoRegular,
            fontSize = 16.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = RobotoMonoRegular,
            fontSize = 14.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = RobotoMonoRegular,
            fontSize = 13.sp
        ),
        bodySmall = TextStyle(
            fontFamily = RobotoMonoRegular,
            fontSize = 12.sp
        ),
        labelLarge = TextStyle(
            fontFamily = RobotoMonoRegular,
            fontSize = 12.sp
        ),
        labelMedium = TextStyle(
            fontFamily = RobotoMonoRegular,
            fontSize = 11.sp
        ),
        labelSmall = TextStyle(
            fontFamily = RobotoMonoThin,
            fontSize = 10.sp
        )
    )
