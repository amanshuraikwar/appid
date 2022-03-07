package io.github.amanshuraikwar.appid.ui

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED", "OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableButtonView(
    modifier: Modifier = Modifier,
    btnIcon: ImageVector,
    btnContentDescription: String,
    btnBackgroundColor: Color,
    btnForegroundColor: Color,
    onButtonClick: () -> Unit,
    btnText: String? = null,
    view: @Composable BoxScope.() -> Unit
) {
    val buttonWidth = 72.dp

    val swipeableState = rememberSwipeableState(1)
    val buttonWidthPx = with(LocalDensity.current) { buttonWidth.toPx() }
    val anchors = mapOf(-buttonWidthPx to 0, 0f to 1)

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            ),
    ) {
        Box(
            Modifier
                .background(btnBackgroundColor)
                .matchParentSize(),
        ) {
            CompositionLocalProvider(
                LocalIndication provides rememberRipple(color = btnForegroundColor)
            ) {
                Column(
                    Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            coroutineScope.launch {
                                onButtonClick()
                                swipeableState.animateTo(1)
                            }
                        }
                        .fillMaxHeight()
                        .width(buttonWidth),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = btnIcon,
                        contentDescription = btnContentDescription,
                        tint = btnForegroundColor,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )

                    if (btnText != null) {
                        Text(
                            text = btnText,
                            modifier = Modifier
                                .padding(top = 8.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.button,
                            color = btnForegroundColor
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .offset {
                    IntOffset(swipeableState.offset.value.roundToInt(), 0)
                }
                .background(MaterialTheme.colors.surface)
                .fillMaxWidth(),
        ) {
            view()
        }
    }
}