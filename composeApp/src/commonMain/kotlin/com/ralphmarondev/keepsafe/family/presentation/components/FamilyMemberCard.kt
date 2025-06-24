package com.ralphmarondev.keepsafe.family.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import keepsafe.composeapp.generated.resources.Res
import keepsafe.composeapp.generated.resources.img
import org.jetbrains.compose.resources.painterResource

@Composable
fun FamilyMemberCard(
    modifier: Modifier = Modifier,
    familyMember: FamilyMember,
    onClick: () -> Unit,
    currentUserUid: String
) {
    OutlinedCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.img),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                val isCurrentUser = familyMember.uid == currentUserUid
                val fullName = familyMember.fullName ?: "No fullName provided."
                val displayName = if (isCurrentUser) {
                    val firstName = fullName.split(" ").firstOrNull() ?: fullName
                    "$firstName (YOU)"
                } else {
                    fullName
                }
                Text(
                    text = displayName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = familyMember.role ?: "No role provided",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}