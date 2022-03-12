package io.github.amanshuraikwar.appid

import android.app.Activity
import android.content.res.Configuration
import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.max
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.doOnLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

fun Activity.isInDarkTheme(): Boolean {
    return application.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}

fun Activity.setupSystemBars(
    isDarkTheme: Boolean = isInDarkTheme()
) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.apply {
        statusBarColor = Color.TRANSPARENT
        navigationBarColor = Color.TRANSPARENT
    }
    window.decorView.doOnLayout {
        WindowInsetsControllerCompat(window, it).apply {
            isAppearanceLightNavigationBars = false
            isAppearanceLightStatusBars = !isDarkTheme
        }
    }
}

inline fun <reified VM : ViewModel> ComponentActivity.getViewModel(
    provider: ViewModelProvider.Factory
): VM {
    return ViewModelProvider(this, provider).get(VM::class.java)
}

@Composable
fun rememberImeAndNavBarInsetsPaddingValues(
    extraPx: Int = 0
): PaddingValues {
    return PaddingValues(
        bottom =
        max(
            rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.systemBars,
                applyTop = false,
                applyBottom = true,
            ).calculateBottomPadding(),
            rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.ime,
                applyTop = false,
                applyBottom = true,
            ).calculateBottomPadding()
        ) + with(LocalDensity.current) { extraPx.toDp() }
    )
}

fun <T> MutableSharedFlow<T>.toSharedFlow(): SharedFlow<T> = this

fun <T> MutableStateFlow<T>.toStateFlow(): StateFlow<T> = this

@Suppress("NOTHING_TO_INLINE")
inline fun Modifier.acmStatusBarsPadding(): Modifier = composed {
    padding(
        rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.statusBars,
            applyTop = true
        )
    )
}

@Suppress("NOTHING_TO_INLINE")
inline fun Modifier.acmNavigationBarsPadding(
    bottom: Boolean = true,
    start: Boolean = true,
    end: Boolean = true,
): Modifier = composed {
    padding(
        rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.navigationBars,
            applyStart = start,
            applyEnd = end,
            applyBottom = bottom
        )
    )
}
