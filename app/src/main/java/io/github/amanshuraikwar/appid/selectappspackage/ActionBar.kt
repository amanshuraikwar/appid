package io.github.amanshuraikwar.appid.selectappspackage

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsPadding
import io.github.amanshuraikwar.appid.ui.IconButton
import io.github.amanshuraikwar.appid.ui.SearchBar

@Composable
internal fun ActionBar(
    onBackClick: () -> Unit,
    onSearch: (query: String) -> Unit,
) {
    Column(
        Modifier.background(MaterialTheme.colors.surface)
    ) {
        Box(
            contentAlignment = Alignment.CenterStart
        ) {
            var inFocus by remember { mutableStateOf(false) }

            val shape = RoundedCornerShape(
                CornerSize(
                    animateDpAsState(
                        targetValue = if (inFocus) {
                            0.dp
                        } else {
                            12.dp
                        }
                    ).value
                )
            )

            val statusBarPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.statusBars
            ).calculateTopPadding()

            val paddingTop by animateDpAsState(
                targetValue = if (inFocus) {
                    0.dp
                } else {
                    statusBarPadding + 12.dp
                }
            )

            val paddingTopInverse by animateDpAsState(
                targetValue = if (inFocus) {
                    statusBarPadding + 12.dp
                } else {
                    0.dp
                }
            )

            val paddingStart by animateDpAsState(
                targetValue = if (inFocus) {
                    0.dp
                } else {
                    72.dp
                }
            )

            val paddingStartInverse by animateDpAsState(
                targetValue = if (inFocus) {
                    72.dp
                } else {
                    0.dp
                }
            )

            val paddingEnd by animateDpAsState(
                targetValue = if (inFocus) {
                    0.dp
                } else {
                    16.dp
                }
            )

            val paddingEndInverse by animateDpAsState(
                targetValue = if (inFocus) {
                    16.dp
                } else {
                    0.dp
                }
            )

            val paddingBottom by animateDpAsState(
                targetValue = if (inFocus) {
                    0.dp
                } else {
                    12.dp
                }
            )

            val paddingBottomInverse by animateDpAsState(
                targetValue = if (inFocus) {
                    12.dp
                } else {
                    0.dp
                }
            )

            Surface(
                Modifier
                    .padding(start = paddingStart, end = paddingEnd)
                    .padding(bottom = paddingBottom, top = paddingTop)
                    .fillMaxWidth(),
                shape = shape,
                elevation = 2.dp,
                color = MaterialTheme.colors.surface
            ) {
                SearchBar(
                    Modifier
                        .padding(top = paddingTopInverse, bottom = paddingBottomInverse)
                        .padding(start = paddingStartInverse, end = paddingEndInverse),
                    onSearch = onSearch,
                    onFocusChange = {
                        inFocus = it.hasFocus
                    }
                )
            }

            IconButton(
                modifier = Modifier
                    .statusBarsPadding()
                    .align(Alignment.CenterStart)
                    .padding(4.dp),
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                onClick = onBackClick
            )
        }

        Divider()
    }
}