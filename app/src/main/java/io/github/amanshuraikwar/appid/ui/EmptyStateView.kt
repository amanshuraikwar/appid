package io.github.amanshuraikwar.appid.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.ui.theme.medium
import io.github.amanshuraikwar.appid.ui.theme.outline

@Composable
fun EmptyStateView(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String,
    title: String,
    description: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = MaterialTheme.colors.outline,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                //.clip(RoundedCornerShape(100))
                .clip(MaterialTheme.shapes.medium)
                //.background(MaterialTheme.colors.outline)
                .padding(16.dp)
                .size(128.dp)
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
            text = title,
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colors.onSurface.medium,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .padding(16.dp),
            text = description,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface.medium,
            textAlign = TextAlign.Center
        )
    }
}