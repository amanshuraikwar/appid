package io.github.amanshuraikwar.appid.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.IntOffset

@Composable
fun AppIdScaffold(
    modifier: Modifier = Modifier,
    actionBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = {
            actionBar()
            content()
            bottomBar()
        }
    ) { measurables, constraints ->
        val actionBarPlaceable = measurables[0].measure(constraints.copy(minHeight = 0))
        val bottomBarPlaceable = measurables[2].measure(constraints.copy(minHeight = 0))
        val contentPlaceable = measurables[1].measure(
            constraints.copy(
                minHeight = 0,
                maxHeight =
                constraints.maxHeight - actionBarPlaceable.height - bottomBarPlaceable.height
            )
        )
        layout(constraints.maxWidth, constraints.maxHeight) {
            actionBarPlaceable.place(
                IntOffset(0, 0),
                zIndex = 1f
            )
            contentPlaceable.place(
                IntOffset(0, actionBarPlaceable.height),
                zIndex = 0f
            )
            bottomBarPlaceable.place(
                IntOffset(
                    0,
                    constraints.maxHeight - bottomBarPlaceable.height
                ),
                zIndex = 1f
            )
        }
    }
}