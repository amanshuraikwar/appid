package io.github.amanshuraikwar.appid.appgroups

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.model.AppGroup
import io.github.amanshuraikwar.appid.ui.AppGroupView
import io.github.amanshuraikwar.appid.ui.SwipeableButtonView
import java.util.*

@Composable
internal fun AppGroupItemView(
    appGroup: AppGroup,
    onClick: (id: String) -> Unit,
    onDeleteClick: (id: String) -> Unit,
) {
    SwipeableButtonView(
        btnIcon = Icons.Rounded.Delete,
        btnContentDescription = "Delete",
        btnBackgroundColor = MaterialTheme.colors.error,
        btnForegroundColor = MaterialTheme.colors.onError,
        onButtonClick = { onDeleteClick(appGroup.id) }
    ) {
        Surface(
            color = MaterialTheme.colors.surface,
            elevation = 2.dp
        ) {
            Column(
                Modifier
                    .clickable {
                        onClick(appGroup.id)
                    }
            ) {
                Column(
                    Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = appGroup.name.uppercase(Locale.getDefault()),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(bottom = 12.dp),
                    )

                    AppGroupView(
                        appGroup = appGroup,
                        lines = 1,
                    )
                }
            }
        }
    }
}