package io.github.amanshuraikwar.appid.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeableState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import kotlin.math.abs
import kotlin.math.roundToInt

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun SwipeDismissView(
    modifier: Modifier = Modifier,
    visible: Boolean,
    onDismiss: () -> Unit,
    enterFromTop: Boolean = true,
    onVisibleStateChange: (EnterExitState) -> Unit = {},
    content: @Composable BoxScope.(
        modifier: Modifier,
        animatedVisibilityScope: AnimatedVisibilityScope
    ) -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = slideInVertically {
            if (enterFromTop) {
                -it
            } else {
                it
            }
        } + fadeIn(),
        exit = slideOutVertically {
            if (enterFromTop) {
                -it
            } else {
                it
            }
        } + fadeOut()
    ) {
        LaunchedEffect(key1 = transition.currentState) {
            onVisibleStateChange(transition.currentState)
        }

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            val swipeableState = rememberSwipeableState(
                1,
                confirmStateChange = {
                    when (it) {
                        0 -> {
                            onDismiss()
                            enterFromTop
                        }
                        1 -> {
                            true
                        }
                        else -> {
                            onDismiss()
                            !enterFromTop
                        }
                    }
                }
            )
            val maxHeightPx = with(LocalDensity.current) { maxHeight.toPx() }
            val offset1 = if (enterFromTop) {
                -maxHeightPx
            } else {
                -maxHeightPx / 4
            }
            val offset3 = if (enterFromTop) {
                maxHeightPx / 4
            } else {
                maxHeightPx
            }

            val anchors = mapOf(offset1 to 0, 0f to 1, offset3 to 2)

            Box(
                Modifier
                    .fillMaxSize()
                    .nestedScroll(swipeableState.PreUpPostDownNestedScrollConnection)
                    .swipeable(
                        state = swipeableState,
                        anchors = anchors,
                        thresholds = { _, _ -> FractionalThreshold(0.5f) },
                        orientation = Orientation.Vertical,
                    ),
            ) {
                Box(
                    modifier = Modifier
                        .height(this@BoxWithConstraints.maxHeight)
                        .alpha(
                            1f - (((abs(swipeableState.offset.value) * 0.8f) * 4) / maxHeightPx)
                        )
                        .background(MaterialTheme.colors.onSurface)
                        .fillMaxWidth()
                )

                content(
                    Modifier
                        .offset {
                            IntOffset(0, swipeableState.offset.value.roundToInt())
                        },
                    this@AnimatedVisibility
                )
            }
        }
    }
}

@ExperimentalMaterialApi
val <T> SwipeableState<T>.PreUpPostDownNestedScrollConnection: NestedScrollConnection
    get() = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val delta = available.toFloat()
            return if (delta < 0 && offset.value > 0.0 && source == NestedScrollSource.Drag) {
                if (offset.value > abs(delta)) {
                    performDrag(delta).toOffset()
                } else {
                    performDrag(-offset.value).toOffset()
                }
            } else if (delta > 0 && offset.value < 0.0 && source == NestedScrollSource.Drag) {
                if (abs(offset.value) > delta) {
                    performDrag(delta).toOffset()
                } else {
                    performDrag(-offset.value).toOffset()
                }
            } else {
                Offset.Zero
            }
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            return if (source == NestedScrollSource.Drag) {
                performDrag(available.toFloat()).toOffset()
            } else {
                Offset.Zero
            }
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            val toFling = Offset(available.x, available.y).toFloat()
            return if (toFling < 0 && offset.value > 0.0) {
                performFling(velocity = toFling)
                available
            } else {
                Velocity.Zero
            }
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            performFling(velocity = Offset(available.x, available.y).toFloat())
            return available
        }

        private fun Float.toOffset(): Offset = Offset(0f, this)

        private fun Offset.toFloat(): Float = this.y
    }