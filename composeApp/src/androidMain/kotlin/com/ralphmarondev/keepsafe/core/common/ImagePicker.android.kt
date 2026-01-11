package com.ralphmarondev.keepsafe.core.common

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.io.File

@Composable
actual fun SelectImageButton(
    onClick: (String) -> Unit,
    text: String,
    modifier: Modifier
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val fileName = "picked_image_${System.currentTimeMillis()}.jpg"
            val file = File(context.filesDir, fileName)
            context.contentResolver.openInputStream(uri)?.use { input ->
                file.outputStream().use { output -> input.copyTo(output) }
            }
            onClick(file.absolutePath)
        }
    }

    TextButton(
        onClick = { launcher.launch("image/*") },
        modifier = modifier
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}