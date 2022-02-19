package io.github.amanshuraikwar.appid.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import io.github.amanshuraikwar.appid.R
import io.github.amanshuraikwar.appid.ui.theme.appName

@Composable
fun ActionBarView(
    modifier: Modifier = Modifier,
    surfaceColor: Color = MaterialTheme.colors.surface,
    elevation: Dp = 4.dp,
    content: @Composable () -> Unit = {
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.appName,
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
                .statusBarsPadding(),
        ) {
            content()
        }
    }
}