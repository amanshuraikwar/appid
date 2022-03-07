package io.github.amanshuraikwar.appid.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.model.App
import io.github.amanshuraikwar.appid.model.AppGroup
import io.github.amanshuraikwar.appid.ui.theme.outline
import kotlin.math.roundToInt

@Composable
fun AppGroupView(
    modifier: Modifier = Modifier,
    appList: List<App>,
    lines: Int? = null,
    onAppClick: ((App) -> Unit)? = null
) {
    SubcomposeLayout(modifier = modifier) { constraints ->
        val iconWidthPx = 56.dp.toPx()
        val iconHeightPx = 56.dp.toPx()
        val minimumHorizontalPadding = 8.dp.toPx()
        val minimumVerticalPadding = 8.dp.toPx()

        val perRow = (constraints.maxWidth / (iconWidthPx + minimumHorizontalPadding)).toInt()

        val totalExtraHorizontalGapPx =
            constraints.maxWidth -
                    ((perRow * iconWidthPx) + ((perRow - 1) * minimumHorizontalPadding))
        val extraHorizontalGapPx = (totalExtraHorizontalGapPx / (perRow - 1)).toInt()

        val horizontalGapPx = (minimumHorizontalPadding + extraHorizontalGapPx).roundToInt()
        // keep extra horizontal gap same as extra vertical gap
        val verticalGapPx = minimumVerticalPadding + extraHorizontalGapPx

        val allApps = appList
        val totalLines =
            lines
                ?: ((appList.size / perRow) + if (appList.size % perRow == 0) 0 else 1)

        val heightPx =
            ((totalLines * iconHeightPx) + ((totalLines - 1) * verticalGapPx)).roundToInt()

        val placeables = mutableListOf<Placeable>()
        for (index in (0 until (perRow * totalLines))) {
            val app = allApps.getOrNull(index)
            val placeable = subcompose(index) {
                if (app != null) {
                    if (index == (perRow * totalLines - 1)) {
                        if (allApps.size - 1 == index) {
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize(),
                                shape = MaterialTheme.shapes.small,
                                color = MaterialTheme.colors.surface,
                                elevation = 2.dp
                            ) {
                                AppIconView(
                                    modifier = Modifier
                                        .let {
                                            if (onAppClick != null) {
                                                it.clickable {
                                                    onAppClick(app)
                                                }
                                            } else {
                                                it
                                            }
                                        }
                                        .fillMaxSize(),
                                    packageName = app.packageName,
                                )
                            }
                        } else {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                shape = MaterialTheme.shapes.small,
                                color = MaterialTheme.colors.surface,
                                border = BorderStroke(
                                    2.dp,
                                    MaterialTheme.colors.outline
                                )
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "+${allApps.size - perRow * totalLines + 1}",
                                    )
                                }
                            }
                        }
                    } else {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize(),
                            shape = MaterialTheme.shapes.small,
                            color = MaterialTheme.colors.surface,
                            elevation = 2.dp
                        ) {
                            AppIconView(
                                modifier = Modifier
                                    .let {
                                        if (onAppClick != null) {
                                            it.clickable {
                                                onAppClick(app)
                                            }
                                        } else {
                                            it
                                        }
                                    }
                                    .fillMaxSize(),
                                packageName = app.packageName,
                            )
                        }
                    }
                } else {
                    Surface(
                        Modifier.fillMaxSize(),
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colors.surface,
                        border = BorderStroke(
                            2.dp,
                            MaterialTheme.colors.outline
                        )
                    ) {
                        Box {}
                    }
                }
            }
                .first()
                .measure(
                    constraints = constraints.copy(
                        maxWidth = iconWidthPx.roundToInt(),
                        maxHeight = iconHeightPx.roundToInt(),
                        minWidth = iconWidthPx.roundToInt(),
                        minHeight = iconHeightPx.roundToInt()
                    )
                )

            placeables.add(
                placeable
            )
        }

        layout(constraints.maxWidth, height = heightPx) {
            placeables.forEachIndexed { index, placeable ->
                val col = index % perRow
                val row = index / perRow
                val offset = IntOffset(
                    x = (col * iconWidthPx).toInt() + (horizontalGapPx * col),
                    y = (row * iconHeightPx).toInt() + (verticalGapPx * row).toInt()
                )
                placeable.place(offset)
            }
        }
    }
}

@Composable
fun AppGroupView(
    modifier: Modifier = Modifier,
    appGroup: AppGroup,
    lines: Int? = null,
    onAppClick: ((App) -> Unit)? = null
) {
    AppGroupView(
        modifier = modifier,
        appList = appGroup.apps,
        lines = lines,
        onAppClick = onAppClick
    )
}