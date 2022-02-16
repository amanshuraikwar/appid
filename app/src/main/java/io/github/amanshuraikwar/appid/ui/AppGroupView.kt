package io.github.amanshuraikwar.appid.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.model.AppGroup

@Composable
fun AppGroupView(
    modifier: Modifier = Modifier,
    appGroup: AppGroup,
    clickable: Boolean = false,
    onClick: () -> Unit = {}
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        elevation = 2.dp
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .clickable(enabled = clickable, onClick = onClick)
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp)
                .height(120.dp)
        ) {
            val width = with(LocalDensity.current) {
                64.dp.toPx()
            }
            val height = with(LocalDensity.current) {
                56.dp.toPx()
            }
            val perRow = (constraints.maxWidth / width).toInt()

            val totalHorizontalGap = with(LocalDensity.current) {
                constraints.maxWidth - perRow * 64.dp.toPx()
            }
            val horizontalGap = (totalHorizontalGap / (perRow - 1)).toInt()

            val verticalGap = with(LocalDensity.current) {
                8.dp.toPx()
            }

            appGroup
                .apps
                .take(perRow * 2).forEachIndexed { index, app ->
                    val col = index % perRow
                    val row = index / perRow

                    AppIconView(
                        modifier = Modifier
                            .offset {
                                IntOffset(
                                    x = (col * width).toInt() + (horizontalGap * col),
                                    y = (row * height).toInt() + (verticalGap * row).toInt()
                                )
                            }
                            .padding(horizontal = 4.dp)
                            .size(56.dp),
                        packageName = app.packageName
                    )
                }
        }
    }
}