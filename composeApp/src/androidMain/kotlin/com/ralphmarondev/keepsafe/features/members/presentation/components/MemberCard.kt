package com.ralphmarondev.keepsafe.features.members.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ralphmarondev.keepsafe.R
import com.ralphmarondev.keepsafe.core.common.DateFormatter
import com.ralphmarondev.keepsafe.core.domain.model.User

@Composable
fun MemberCard(
    modifier: Modifier = Modifier,
    member: User,
    onClick: () -> Unit
) {
    val isPreview = LocalInspectionMode.current
    val contentDescription = "${member.firstName} ${member.lastName}"
    val imageModifier = Modifier
        .size(64.dp)
        .clip(RoundedCornerShape(16.dp))

    OutlinedCard(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isPreview) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = contentDescription,
                    modifier = imageModifier,
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = member.photoUrl,
                    contentDescription = contentDescription,
                    modifier = imageModifier,
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    error = painterResource(R.drawable.ic_launcher_foreground),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "${member.firstName} ${member.lastName}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = DateFormatter.format(member.birthday),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}