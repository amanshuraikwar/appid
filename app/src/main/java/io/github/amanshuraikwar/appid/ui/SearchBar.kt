package io.github.amanshuraikwar.appid.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import io.github.amanshuraikwar.appid.ui.theme.medium

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (query: String) -> Unit,
    onFocusChange: (state: FocusState) -> Unit = {}
) {
    var searchString by remember {
        mutableStateOf("")
    }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = searchString) {
        onSearch(searchString)
    }

    Box(
        modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        if (searchString.isEmpty()) {
            Text(
                text = "Search application id...",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                color = MaterialTheme.colors.onSurface.medium,
                style = MaterialTheme.typography.subtitle1,
            )
        }

        BasicTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusChanged(onFocusChange)
                .align(Alignment.CenterStart)
                .fillMaxWidth(),
            value = searchString,
            onValueChange = { newValue ->
                searchString = newValue
            },
            textStyle = MaterialTheme.typography.subtitle1.merge(
                TextStyle(
                    color = MaterialTheme.colors.onSurface
                )
            ),
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colors.primary),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(searchString)
                }
            )
        ) { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            ) {
                innerTextField()
            }
        }

        IconButton(
            modifier = Modifier
                .alpha(
                    if (searchString.isEmpty()) {
                        0f
                    } else {
                        1f
                    }
                )
                .align(Alignment.CenterEnd),
            imageVector = Icons.TwoTone.Clear,
            contentDescription = "Clear",
            enabled = searchString.isNotEmpty(),
            onClick = {
                searchString = ""
            }
        )
    }
}