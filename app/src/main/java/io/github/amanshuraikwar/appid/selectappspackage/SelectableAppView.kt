package io.github.amanshuraikwar.appid.selectappspackage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.ui.AppIconView
import io.github.amanshuraikwar.appid.ui.theme.disabled
import io.github.amanshuraikwar.appid.ui.theme.outline
import io.github.amanshuraikwar.appid.ui.theme.packageName

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun SelectableAppView(
    modifier: Modifier = Modifier,
    selectableApp: SelectableApp,
    onClick: (() -> Unit)? = null,
    appIconSize: Dp = 40.dp
) {
    val app = selectableApp.app

    val bgColor by animateColorAsState(
        targetValue = if (selectableApp.selected) {
            MaterialTheme.colors.outline
        } else {
            MaterialTheme.colors.surface
        }
    )

    Surface(
        modifier
            .fillMaxWidth(),
        color = bgColor
    ) {
        Box(
            Modifier
                .clickable(enabled = onClick != null) {
                    onClick?.invoke()
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
                Modifier
                    .fillMaxWidth()
                    .padding(start = appIconSize + 16.dp, end = 24.dp + 16.dp)
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


            Icon(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    //.background(MaterialTheme.colors.primary.disabled)
                    //.padding(8.dp)
                    .size(24.dp),
                imageVector = Icons.Rounded.CheckBoxOutlineBlank,
                contentDescription = "Checked Outline",
                tint = MaterialTheme.colors.onSurface
            )

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.CenterEnd),
                visible = selectableApp.selected,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Icon(
                    modifier = Modifier
                        //.background(MaterialTheme.colors.primary.disabled)
                        //.padding(8.dp)
                        .size(24.dp),
                    imageVector = Icons.Rounded.CheckBox,
                    contentDescription = "Checked",
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}