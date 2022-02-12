package io.github.amanshuraikwar.appid.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.model.App

@Composable
fun AppView(
    modifier: Modifier = Modifier,
    app: App
) {
    Surface(
        modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .padding(16.dp)
        ) {
            AppIconView(
                modifier = Modifier.size(56.dp),
                packageName = app.packageName
            )

            Column(
                Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = app.name,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = app.packageName,
                    style = MaterialTheme.typography.body2
                )

                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colors.primary)
                        .padding(vertical = 2.dp, horizontal = 4.dp),
                    text = app.versionName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}