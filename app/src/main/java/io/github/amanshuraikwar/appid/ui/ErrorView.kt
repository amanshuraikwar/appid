package io.github.amanshuraikwar.appid.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    enterFromBottom: Boolean = true,
    @Suppress("NAME_SHADOWING")
    enter: (enterFromBottom: Boolean) -> EnterTransition = { enterFromBottom ->
        if (enterFromBottom) {
            slideInVertically { it } + fadeIn()
        } else {
            slideInVertically { -it } + fadeIn()
        }
    },
    @Suppress("NAME_SHADOWING")
    exit: (enterFromBottom: Boolean) -> ExitTransition = { enterFromBottom ->
        if (enterFromBottom) {
            slideOutVertically { it } + fadeOut()
        } else {
            slideOutVertically { -it } + fadeOut()
        }
    },
    error: UiError?
) {
    var message by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = error) {
        if (error != null) {
            message = error.text
        }
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = error != null,
        enter = enter(enterFromBottom),
        exit = exit(enterFromBottom)
    ) {
        Surface(
            modifier = Modifier
//                .padding(4.dp)
                .fillMaxWidth(),
//            elevation = 1.dp,
            color = MaterialTheme.colors.onError,
//            shape = MaterialTheme.shapes.small
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp)
                        .size(24.dp),
                    imageVector = Icons.Rounded.Error,
                    contentDescription = "Error",
                    tint = MaterialTheme.colors.error
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(vertical = 16.dp)
                        .padding(end = 16.dp),
                    text = message,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}