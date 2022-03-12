package io.github.amanshuraikwar.appid.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.BuildConfig
import io.github.amanshuraikwar.appid.R
import io.github.amanshuraikwar.appid.acmStatusBarsPadding
import io.github.amanshuraikwar.appid.ui.theme.appName
import io.github.amanshuraikwar.appid.ui.theme.medium

@Composable
fun ActionBarView(
    modifier: Modifier = Modifier,
    surfaceColor: Color = MaterialTheme.colors.surface,
    elevation: Dp = 4.dp,
    content: @Composable BoxScope.() -> Unit = {
        Text(
            modifier = Modifier//.statusBarsPadding()
                .padding(16.dp),
            text = stringResource(id = R.string.app_name).uppercase(),
            style = MaterialTheme.typography.appName,
            color = MaterialTheme.colors.primary
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(16.dp),
            text = BuildConfig.VERSION_NAME,
            style = MaterialTheme.typography.appName,
            color = MaterialTheme.colors.onSurface.medium
        )
    }
) {
    Surface(
        modifier = modifier,
        color = surfaceColor,
        elevation = elevation
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 56.dp)
                .acmStatusBarsPadding(),
            contentAlignment = Alignment.CenterStart
        ) {
            content()
        }
    }
}