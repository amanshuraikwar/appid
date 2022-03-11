package io.github.amanshuraikwar.appid.appgroupdetail

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.ViewList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.model.AppGroup
import io.github.amanshuraikwar.appid.ui.ActionBarView
import io.github.amanshuraikwar.appid.ui.IconButton
import io.github.amanshuraikwar.appid.ui.rememberAppIdIndication

@Composable
fun ActionBar(
    state: AppGroupDetailState,
    onBackClick: () -> Unit,
    onGridViewClick: () -> Unit,
    onListViewClick: () -> Unit,
    onDeleteAppGroupClick: (AppGroup) -> Unit,
) {
    CompositionLocalProvider(
        LocalIndication provides rememberAppIdIndication(
            color = MaterialTheme.colors.onPrimary
        )
    ) {
        ActionBarView(
            elevation = 0.dp,
            surfaceColor = MaterialTheme.colors.primary
        ) {
            Column {
                Box(
                    Modifier.fillMaxWidth(),
                ) {
                    IconButton(
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 4.dp),
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back",
                        onClick = onBackClick,
                        foregroundColor = MaterialTheme.colors.onPrimary
                    )

                    if (state is AppGroupDetailState.Success) {
                        IconButton(
                            modifier = Modifier
                                .padding(end = 56.dp)
                                .padding(horizontal = 4.dp, vertical = 4.dp)
                                .align(Alignment.CenterEnd),
                            imageVector = when (state.appDisplayType) {
                                AppGroupDetailState.AppDisplayType.GRID -> {
                                    Icons.Rounded.GridView
                                }
                                AppGroupDetailState.AppDisplayType.LIST -> {
                                    Icons.Rounded.ViewList
                                }
                            },
                            contentDescription = "Grid View",
                            foregroundColor = MaterialTheme.colors.onPrimary,
                            onClick = when (state.appDisplayType) {
                                AppGroupDetailState.AppDisplayType.GRID -> onListViewClick
                                AppGroupDetailState.AppDisplayType.LIST -> onGridViewClick
                            }
                        )

                        IconButton(
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 4.dp)
                                .align(Alignment.CenterEnd),
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = "Delete",
                            onClick = {
                                onDeleteAppGroupClick(state.appGroup)
                            },
                            foregroundColor = MaterialTheme.colors.onPrimary
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .padding(top = 48.dp, start = 16.dp, bottom = 24.dp, end = 16.dp),
                    text = when (state) {
                        is AppGroupDetailState.AppGroupNotFound -> "App group not found"
                        AppGroupDetailState.Loading -> ""
                        is AppGroupDetailState.Success -> state.appGroup.name
                    },
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}