package io.github.amanshuraikwar.appid.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.RemoveCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.model.App
import io.github.amanshuraikwar.appid.ui.swipe.SwipeAction
import io.github.amanshuraikwar.appid.ui.swipe.SwipeableActionsBox
import io.github.amanshuraikwar.appid.ui.theme.onRemove
import io.github.amanshuraikwar.appid.ui.theme.packageName
import io.github.amanshuraikwar.appid.ui.theme.remove

@Composable
fun AppView(
    modifier: Modifier = Modifier,
    app: App,
    onClick: ((App) -> Unit)? = null,
    appIconSize: Dp = 40.dp
) {
    Surface(
        modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .clickable(enabled = onClick != null) {
                    onClick?.invoke(app)
                }
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colors.surface,
                elevation = 1.dp
            ) {
                AppIconView(
                    modifier = Modifier.size(appIconSize),
                    packageName = app.packageName
                )
            }

            Column(
                Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = app.name,
                    style = MaterialTheme.typography.subtitle1,
                )

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = app.packageName.lowercase(),
                    style = MaterialTheme.typography.packageName,
                )

                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colors.primary)
                        .padding(vertical = 2.dp, horizontal = 4.dp),
                    text = app.versionName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
fun AppView(
    modifier: Modifier = Modifier,
    app: App,
    appIconSize: Dp = 40.dp,
    onClick: (App) -> Unit,
    onDeleteClick: (App) -> Unit,
    onUninstallClick: (App) -> Unit,
) {
    val delete = SwipeAction(
        icon = {
            Icon(
                modifier = Modifier.padding(16.dp),
                imageVector = Icons.TwoTone.RemoveCircle,
                tint = MaterialTheme.colors.onRemove,
                contentDescription = "Remove"
            )
        },
        background = MaterialTheme.colors.remove,
        onSwipe = { onDeleteClick(app) },
        isUndo = false,
    )

    val uninstall = SwipeAction(
        icon = {
            Icon(
                modifier = Modifier.padding(16.dp),
                imageVector = Icons.TwoTone.Delete,
                tint = MaterialTheme.colors.onError,
                contentDescription = "Delete"
            )
        },
        background = MaterialTheme.colors.error,
        onSwipe = { onUninstallClick(app) },
        isUndo = false,
    )

    SwipeableActionsBox(
        modifier = modifier,
        endActions = listOf(delete, uninstall),
        backgroundUntilSwipeThreshold = MaterialTheme.colors.onPrimary
    ) {
        AppView(app = app, onClick = onClick, appIconSize = appIconSize)
    }
}