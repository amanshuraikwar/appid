package io.github.amanshuraikwar.appid.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    foregroundColor: Color = MaterialTheme.colors.onSurface,
    enabled: Boolean = true,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = foregroundColor,
        modifier = modifier
            .clip(shape = MaterialTheme.shapes.small)
            .clickable(enabled = enabled, onClick = onClick)
            .padding(12.dp)
            .size(24.dp)
    )
}