package com.ralphmarondev.keepsafe.auth.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun UsernameTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = {
            Text(
                text = "Username",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.AccountBox,
                contentDescription = "Username",
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        trailingIcon = {
            AnimatedVisibility(value.isNotBlank()) {
                IconButton(
                    onClick = {
                        onValueChange("")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = "Clear",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        },
        textStyle = TextStyle(
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
            color = MaterialTheme.colorScheme.secondary
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true
    )
}