package io.github.amanshuraikwar.appid.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.BuildConfig
import io.github.amanshuraikwar.appid.R
import io.github.amanshuraikwar.appid.acmNavigationBarsPadding
import io.github.amanshuraikwar.appid.acmStatusBarsPadding
import io.github.amanshuraikwar.appid.ui.theme.appName
import io.github.amanshuraikwar.appid.ui.theme.appNameLarge
import io.github.amanshuraikwar.appid.ui.theme.disabled
import io.github.amanshuraikwar.appid.ui.theme.medium
import kotlin.math.roundToInt

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AboutView(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
    ) {
        val swipeableState = rememberSwipeableState(
            1,
            confirmStateChange = {
                if (it == 0) {
                    onBackClick()
                }
                true
            }
        )
        val maxHeightPx = with(LocalDensity.current) { maxHeight.toPx() }
        val anchors = mapOf(-maxHeightPx to 0, 0f to 1)

        Box(
            Modifier
                .fillMaxSize()
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Vertical
                ),
        ) {
            Surface(
                modifier = Modifier
                    .offset {
                        IntOffset(0, swipeableState.offset.value.roundToInt())
                    },
                color = MaterialTheme.colors.primary,
                elevation = 2.dp
            ) {
                Box(
                    modifier = Modifier
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
                            color = MaterialTheme.colors.onPrimary,

                            )

                        Text(
                            modifier = Modifier.padding(top = 16.dp),
                            text = BuildConfig.VERSION_NAME,
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onPrimary.medium,
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
                    )
                    .offset {
                        IntOffset(0, swipeableState.offset.value.roundToInt())
                    },
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
                        imageVector = Icons.Rounded.ExpandLess,
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
}