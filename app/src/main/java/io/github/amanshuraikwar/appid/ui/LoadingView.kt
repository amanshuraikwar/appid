package io.github.amanshuraikwar.appid.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.ui.theme.medium

@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
    text: String,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = text,
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onBackground.medium,
            fontWeight = FontWeight.Medium
        )

        ProgressView(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        )
    }
}