package com.ralphmarondev.keepsafe.settings.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun SyncWithFirebaseDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = "Confirm Sync"
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancel"
                )
            }
        },
        text = {
            Text(
                text = "Confirming will delete all of the data on this device and replace with the data from the cloud. This action cannot be undone.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        title = {
            Text(
                text = "Sync with Firebase",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    )
}