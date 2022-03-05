package io.github.amanshuraikwar.appid.createappgroup

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Title
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.model.App
import io.github.amanshuraikwar.appid.rememberImeAndNavBarInsetsPaddingValues
import io.github.amanshuraikwar.appid.ui.ActionBarView
import io.github.amanshuraikwar.appid.ui.ActionButton
import io.github.amanshuraikwar.appid.ui.AppGroupView
import io.github.amanshuraikwar.appid.ui.AppIdScaffold
import io.github.amanshuraikwar.appid.ui.theme.disabled

@Composable
internal fun AppGroupDetailView(
    modifier: Modifier = Modifier,
    appList: List<App>,
    onCloseClick: () -> Unit,
    onCreateAppGroupClick: (appGroupName: String) -> Unit,
    onSelectAppsClick: () -> Unit,
    onClearAppsClick: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var appGroupName by remember {
        mutableStateOf("")
    }

    AppIdScaffold(
        modifier.fillMaxSize(),
        actionBar = {
            ActionBarView {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close",
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                        .clip(shape = RoundedCornerShape(100))
                        .clickable(onClick = onCloseClick)
                        .padding(horizontal = 12.dp, vertical = 12.dp)
                        .size(24.dp)
                )
            }
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colors.surface
            ) {
                Column(
                    Modifier
                        .padding(rememberImeAndNavBarInsetsPaddingValues())
                ) {
                    Divider()

                    ActionButton(
                        Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        text = "Create App Group",
                        onClick = {
                            onCreateAppGroupClick(appGroupName)
                        }
                    )
                }
            }
        }
    ) {
        Column(
            Modifier
                .background(MaterialTheme.colors.surface)
                .fillMaxSize()
                .clickable { focusRequester.requestFocus() }
        ) {
            BasicTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                value = appGroupName,
                onValueChange = { newValue ->
                    appGroupName = newValue
                },
                textStyle = MaterialTheme.typography.h5.merge(
                    TextStyle(
                        color = MaterialTheme.colors.onSurface
                    )
                ),
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colors.primary),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = false,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {

                    }
                )
            ) { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Title,
                        contentDescription = "Title",
                        tint = animateColorAsState(
                            targetValue = if (appGroupName.isEmpty()) {
                                MaterialTheme.colors.onSurface.disabled
                            } else {
                                MaterialTheme.colors.primary
                            }
                        ).value,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .size(24.dp)
                    )

                    if (appGroupName.isEmpty()) {
                        Text(
                            text = "App group name",
                            modifier = Modifier
                                .padding(start = 56.dp, end = 16.dp)
                                .align(Alignment.CenterStart),
                            color = MaterialTheme.colors.onSurface.disabled,
                            style = MaterialTheme.typography.h5,
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 56.dp, end = 16.dp)
                            .padding(vertical = 8.dp)
                    ) {
                        innerTextField()
                    }
                }
            }

            Divider()

            Column(
                modifier = Modifier
                    .animateContentSize()
                    .clickable(onClick = onSelectAppsClick)
                    .fillMaxWidth(),
            ) {
                Box(
                    modifier = Modifier
                        .clickable(onClick = onSelectAppsClick)
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Apps,
                        contentDescription = "Apps",
                        tint = animateColorAsState(
                            targetValue = if (appList.isEmpty()) {
                                MaterialTheme.colors.onSurface.disabled
                            } else {
                                MaterialTheme.colors.primary
                            }
                        ).value,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .size(24.dp)
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = 56.dp, end = 16.dp),
                        text = if (appList.isEmpty()) {
                            "Click to select apps"
                        } else {
                            "Selected apps"
                        },
                        color = animateColorAsState(
                            targetValue = if (appList.isEmpty()) {
                                MaterialTheme.colors.onSurface.disabled
                            } else {
                                MaterialTheme.colors.primary
                            }
                        ).value,
                        style = MaterialTheme.typography.body1,
                    )
                }

                if (appList.isNotEmpty()) {
                    AppGroupView(
                        modifier = Modifier
                            .padding(
                                start = 56.dp,
                                end = 16.dp,
                                bottom = 2.dp,
                                top = 2.dp
                            ),
                        appList = appList
                    )

                    Text(
                        text = "Clear Apps",
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier
                            .padding(
                                start = 56.dp,
                                end = 16.dp,
                                bottom = 8.dp,
                                top = 8.dp
                            )
                            .align(Alignment.End)
                            .clip(MaterialTheme.shapes.small)
                            .clickable(onClick = onClearAppsClick)
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    )
                }
            }

            Divider()
        }
    }
}