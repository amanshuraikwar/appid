package io.github.amanshuraikwar.appid.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

data class UiError(val text: String)

fun ViewModel.getUiErrorFlow(): MutableSharedFlow<UiError?> {
    return MutableSharedFlow(
        extraBufferCapacity = 2,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
}

fun MutableSharedFlow<UiError?>.tryEmitError(message: String) {
    tryEmit(null)
    tryEmit(UiError(text = message))
    tryEmit(null)
}

@Composable
fun SharedFlow<UiError?>.collectAsUiErrorState(): State<UiError?> {
    return produceState<UiError?>(null, this) {
        collect {
            value = it
            if (it != null) {
                delay(3000)
            }
        }
    }
}