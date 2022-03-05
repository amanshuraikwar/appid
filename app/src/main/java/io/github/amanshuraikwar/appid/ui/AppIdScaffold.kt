package io.github.amanshuraikwar.appid.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity

@Composable
fun AppIdScaffold(
    modifier: Modifier = Modifier,
    actionBar: @Composable BoxScope.() -> Unit,
    bottomBar: @Composable BoxScope.() -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier,
    ) {
        var searchBarHeight by remember { mutableStateOf(0) }
        var bottomBarHeight by remember { mutableStateOf(0) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = animateDpAsState(
                        targetValue = with(LocalDensity.current) {
                            searchBarHeight.toDp()
                        }
                    ).value,
                    bottom = animateDpAsState(
                        targetValue = with(LocalDensity.current) {
                            bottomBarHeight.toDp()
                        }
                    ).value,
                )
        ) {
            content()
        }

        Box(
            modifier = Modifier.onSizeChanged {
                searchBarHeight = it.height
            }
        ) {
            actionBar()
        }

        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .onSizeChanged {
                    bottomBarHeight = it.height
                }
        ) {
            bottomBar()
        }
    }
}