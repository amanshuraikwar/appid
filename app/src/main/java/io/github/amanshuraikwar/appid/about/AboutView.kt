package io.github.amanshuraikwar.appid.about

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ExpandLess
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.BuildConfig
import io.github.amanshuraikwar.appid.R
import io.github.amanshuraikwar.appid.acmNavigationBarsPadding
import io.github.amanshuraikwar.appid.ui.theme.appName
import io.github.amanshuraikwar.appid.ui.theme.appNameLarge
import io.github.amanshuraikwar.appid.ui.theme.aqua
import io.github.amanshuraikwar.appid.ui.theme.aquaVariant
import io.github.amanshuraikwar.appid.ui.theme.cementDark
import io.github.amanshuraikwar.appid.ui.theme.disabled
import io.github.amanshuraikwar.appid.ui.theme.medium

@Composable
fun AboutView(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    BoxWithConstraints(modifier.fillMaxSize()) {
        Surface(
            color = MaterialTheme.colors.primary,
            elevation = 2.dp
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Brush.linearGradient(
                            0f to aqua,
                            1f to aquaVariant
                        )
                    )
                    .fillMaxWidth()
                    .height(
                        this@BoxWithConstraints.maxHeight / 2
                    ),
            ) {
                Column(
                    Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name).uppercase(),
                        style = MaterialTheme.typography.appNameLarge,
                        color = cementDark,

                        )

                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = BuildConfig.VERSION_NAME,
                        style = MaterialTheme.typography.h6,
                        color = cementDark.medium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Surface(
            modifier = Modifier
                .padding(
                    top = this@BoxWithConstraints.maxHeight / 2
                )
                .fillMaxWidth()
                .height(
                    this@BoxWithConstraints.maxHeight / 2
                ),
            color = MaterialTheme.colors.surface,
            elevation = 2.dp
        ) {
            Column(
                Modifier
                    .clickable(onClick = onBackClick)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = "by amanshu raikwar",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.appName,
                    color = MaterialTheme.colors.onSurface.medium
                )

                Icon(
                    imageVector = Icons.TwoTone.ExpandLess,
                    contentDescription = "Close",
                    modifier = Modifier
                        .fillMaxWidth()
                        .acmNavigationBarsPadding()
                        .size(72.dp),
                    tint = MaterialTheme.colors.onSurface.disabled
                )
            }
        }
    }
}