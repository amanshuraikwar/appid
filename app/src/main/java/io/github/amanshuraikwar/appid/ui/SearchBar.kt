package io.github.amanshuraikwar.appid.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (query: String) -> Unit = {},
) {
    Surface(
        modifier,
        color = MaterialTheme.colors.surface,
        elevation = 8.dp
    ) {
        var searchString by remember {
            mutableStateOf("")
        }

        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(key1 = searchString) {
            onSearch(searchString)
        }

        Box(
            Modifier.statusBarsPadding()
        ) {
            if (searchString.isEmpty()) {
                Text(
                    text = "Search application id...",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .fillMaxWidth()
                        .padding(horizontal = 72.dp, vertical = 12.dp),
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.subtitle1,
                )
            }

            BasicTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
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
                    capitalization = KeyboardCapitalization.Words,
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
                        .padding(horizontal = 72.dp, vertical = 16.dp)
                ) {
                    innerTextField()
                }
            }

            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clip(shape = MaterialTheme.shapes.small)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .size(24.dp)
            )

            if (searchString.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = "Clear",
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clip(shape = RoundedCornerShape(100))
                        .clickable {
                            searchString = ""
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .size(24.dp)
                )
            }
        }
    }
}