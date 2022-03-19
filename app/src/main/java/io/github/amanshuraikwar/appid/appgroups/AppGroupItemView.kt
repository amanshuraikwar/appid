package io.github.amanshuraikwar.appid.appgroups

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.RemoveCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.model.AppGroup
import io.github.amanshuraikwar.appid.ui.AppGroupView
import io.github.amanshuraikwar.appid.ui.swipe.SwipeAction
import io.github.amanshuraikwar.appid.ui.swipe.SwipeableActionsBox
import io.github.amanshuraikwar.appid.ui.theme.onRemove
import io.github.amanshuraikwar.appid.ui.theme.remove
import java.util.*

@Composable
internal fun AppGroupItemView(
    modifier: Modifier = Modifier,
    appGroup: AppGroup,
    onClick: (id: String) -> Unit,
    onDeleteClick: (id: String) -> Unit,
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
        onSwipe = { onDeleteClick(appGroup.id) },
        isUndo = false,
    )

    SwipeableActionsBox(
        modifier = modifier,
        endActions = listOf(delete),
        backgroundUntilSwipeThreshold = MaterialTheme.colors.onPrimary
    ) {
        Surface(
            color = MaterialTheme.colors.surface,
        ) {
            Column(
                Modifier
                    .clickable {
                        onClick(appGroup.id)
                    }
                    .padding(top = 16.dp)
            ) {
                Text(

                    text = appGroup.name.uppercase(Locale.getDefault()),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .padding(horizontal = 16.dp),
                )

                AppGroupView(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    appGroup = appGroup,
                    lines = 1,
                    appIconSize = 40.dp
                )

                Divider(
                    Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}