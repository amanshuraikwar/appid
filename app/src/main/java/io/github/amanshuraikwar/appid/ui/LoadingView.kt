package io.github.amanshuraikwar.appid.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.appgroupdetail.ProgressView

@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
    text: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProgressView(
            Modifier.padding(16.dp)
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = text,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onBackground
        )
    }
}