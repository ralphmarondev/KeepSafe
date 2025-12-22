package com.ralphmarondev.keepsafe.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EmailField(
    email: String,
    onEmailChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    supportingText: String = "",
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        modifier = modifier,
        shape = RoundedCornerShape(20),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.secondary
        ),
        placeholder = {
            Text(
                text = "you@keepsafe.com",
                color = MaterialTheme.colorScheme.secondary
            )
        },
        label = {
            Text(
                text = "Email"
            )
        },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.MailOutline,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        trailingIcon = {
            AnimatedVisibility(visible = email.isNotEmpty()) {
                IconButton(
                    onClick = { onEmailChange("") }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = "Clear email",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        },
        isError = isError,
        supportingText = {
            Text(
                text = supportingText,
                color = MaterialTheme.colorScheme.error
            )
        },
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions
    )
}