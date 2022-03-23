package io.github.amanshuraikwar.appid.createappgroup

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CatchingPokemon
import androidx.compose.material.icons.twotone.ClearAll
import androidx.compose.material.icons.twotone.ExpandMore
import androidx.compose.material.icons.twotone.Title
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.acmStatusBarsPadding
import io.github.amanshuraikwar.appid.model.App
import io.github.amanshuraikwar.appid.rememberImeAndNavBarInsetsPaddingValues
import io.github.amanshuraikwar.appid.ui.ActionButton
import io.github.amanshuraikwar.appid.ui.AppIdScaffold
import io.github.amanshuraikwar.appid.ui.AppView
import io.github.amanshuraikwar.appid.ui.ErrorView
import io.github.amanshuraikwar.appid.ui.IconButton
import io.github.amanshuraikwar.appid.ui.UiError
import io.github.amanshuraikwar.appid.ui.theme.disabled

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun AppGroupDetailView(
    modifier: Modifier = Modifier,
    appGroupName: String,
    onAppGroupNameValueChange: (String) -> Unit,
    appList: List<App>,
    error: UiError?,
    onCloseClick: () -> Unit,
    onCreateAppGroupClick: (appGroupName: String) -> Unit,
    onSelectAppsClick: () -> Unit,
    onClearAppsClick: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    AppIdScaffold(
        modifier
            .fillMaxSize(),
        actionBar = {
            Column {
                Icon(
                    imageVector = Icons.TwoTone.ExpandMore,
                    contentDescription = "Close",
                    modifier = Modifier
                        .clickable(onClick = onCloseClick)
                        .fillMaxWidth()
                        .acmStatusBarsPadding()
                        .padding(8.dp)
                        .size(40.dp)
                )

                Divider()
            }
        },
        bottomBar = {
            Column(
                Modifier
                    .background(MaterialTheme.colors.surface)
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
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                Modifier
                    .fillMaxSize()
            ) {
                BasicTextField(
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    value = appGroupName,
                    onValueChange = onAppGroupNameValueChange,
                    textStyle = MaterialTheme.typography.h5.merge(
                        TextStyle(
                            color = MaterialTheme.colors.onSurface,
                            fontWeight = FontWeight.Medium
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
                            imageVector = Icons.TwoTone.Title,
                            contentDescription = "Title",
                            tint = animateColorAsState(
                                targetValue = if (appGroupName.isEmpty()) {
                                    MaterialTheme.colors.onSurface.disabled
                                } else {
                                    MaterialTheme.colors.primary
                                }
                            ).value,
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .size(24.dp)
                        )

                        if (appGroupName.isEmpty()) {
                            Text(
                                text = "App group name",
                                modifier = Modifier
                                    .padding(start = 72.dp, end = 16.dp)
                                    .align(Alignment.CenterStart),
                                color = MaterialTheme.colors.onSurface.disabled,
                                style = MaterialTheme.typography.h5,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 72.dp, end = 16.dp)
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
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.CatchingPokemon,
                            contentDescription = "Apps",
                            tint = animateColorAsState(
                                targetValue = if (appList.isEmpty()) {
                                    MaterialTheme.colors.onSurface.disabled
                                } else {
                                    MaterialTheme.colors.primary
                                }
                            ).value,
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .padding(horizontal = 24.dp)
                                .size(24.dp)
                        )

                        Text(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .padding(start = 72.dp, end = 16.dp),
                            text = if (appList.isEmpty()) {
                                "Click to select apps"
                            } else {
                                "Selected apps"
                            }.uppercase(),
                            color = animateColorAsState(
                                targetValue = if (appList.isEmpty()) {
                                    MaterialTheme.colors.onSurface.disabled
                                } else {
                                    MaterialTheme.colors.primary
                                }
                            ).value,
                            style = MaterialTheme.typography.button,
                        )

                        if (appList.isNotEmpty()) {
                            IconButton(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 4.dp),
                                imageVector = Icons.TwoTone.ClearAll,
                                contentDescription = "Clear All",
                                onClick = onClearAppsClick,
                                foregroundColor = MaterialTheme.colors.error
                            )
                        }
                    }

                    if (appList.isNotEmpty()) {
                        Divider()

                        LazyColumn {
                            items(
                                items = appList,
                                key = { it.packageName }
                            ) { item ->
                                AppView(
                                    modifier = Modifier
                                        .animateItemPlacement(),
                                    app = item,
                                    appIconSize = 40.dp,
                                )
                            }
                        }
                    }
                }

                Divider()
            }

            ErrorView(
                error = error
            )
        }
    }
}