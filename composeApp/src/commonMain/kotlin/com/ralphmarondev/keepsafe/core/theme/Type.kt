package com.ralphmarondev.keepsafe.core.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import keepsafe.composeapp.generated.resources.Res
import keepsafe.composeapp.generated.resources.roboto_mono_bold
import keepsafe.composeapp.generated.resources.roboto_mono_regular
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

val Typography: Typography
    @Composable get() = Typography(
        bodyLarge = TextStyle(
            fontFamily = RobotoMonoBold,
            fontWeight = FontWeight.Normal,
            fontSize = 17.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = RobotoMonoRegular,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        ),
        bodySmall = TextStyle(
            fontFamily = RobotoMonoRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp
        ),
        titleLarge = TextStyle(
            fontFamily = RobotoMonoBold,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        ),
        titleMedium = TextStyle(
            fontFamily = RobotoMonoRegular,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        ),
        titleSmall = TextStyle(
            fontFamily = RobotoMonoRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 17.sp
        )
    )
