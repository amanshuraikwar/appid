package io.github.amanshuraikwar.appid.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import io.github.amanshuraikwar.appid.R
import io.github.amanshuraikwar.appid.ui.theme.appName

@Composable
fun ActionBarView() {
    Surface(
        color = MaterialTheme.colors.surface,
        elevation = 8.dp
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp),
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.appName,
        )
    }
}