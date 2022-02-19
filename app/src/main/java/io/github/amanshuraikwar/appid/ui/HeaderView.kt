package io.github.amanshuraikwar.appid.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun HeaderView(
    modifier: Modifier = Modifier,
    title: String
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface,
    ) {
        Text(
            text = title.uppercase(Locale.ROOT),
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 8.dp,
                    end = 16.dp,
                    bottom = 8.dp
                ),
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.overline
        )
    }
}