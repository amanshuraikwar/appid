package io.github.amanshuraikwar.appid.ui.theme

import android.os.Build
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.insets.ProvideWindowInsets
import io.github.amanshuraikwar.appid.ui.rememberAppIdIndication

private val DarkColorPalette = darkColors(
    primary = blueLighter,
    primaryVariant = blue,
    secondary = blueLighter,
    background = black,
    surface = black,
    error = redLighter,
    onPrimary = gray900,
    onSecondary = gray900,
    onBackground = blueGrey50,
    onSurface = blueGrey50,
    onError = gray900
)

private val LightColorPalette = lightColors(
    primary = blue,
    primaryVariant = blueDark,
    secondary = blue,
    secondaryVariant = blueDark,
    background = blueGrey50,
    surface = white,
    error = red,
    onPrimary = blueGrey50,
    onSecondary = blueGrey50,
    onBackground = blueGreyDark,
    onSurface = blueGreyDark,
    onError = blueGrey50
)

@Composable
fun AppIdTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val lightColorPalette: Colors
    val darkColorPalette: Colors

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val context = LocalContext.current
        lightColorPalette = dynamicLightColorScheme(context).let { colorScheme ->
            lightColors(
                primary = colorScheme.primary,
                primaryVariant = colorScheme.primary,
                secondary = colorScheme.secondary,
                secondaryVariant = colorScheme.secondary,
                background = colorScheme.background,
                surface = colorScheme.surface,
                error = colorScheme.error,
                onPrimary = colorScheme.onPrimary,
                onSecondary = colorScheme.onSecondary,
                onBackground = colorScheme.onBackground,
                onSurface = colorScheme.onSurface,
                onError = colorScheme.onError
            )
        }

        darkColorPalette = dynamicDarkColorScheme(context).let { colorScheme ->
            darkColors(
                primary = colorScheme.primary,
                primaryVariant = colorScheme.primary,
                secondary = colorScheme.secondary,
                secondaryVariant = colorScheme.secondary,
                background = colorScheme.background,
                surface = colorScheme.surface,
                error = colorScheme.error,
                onPrimary = colorScheme.onPrimary,
                onSecondary = colorScheme.onSecondary,
                onBackground = colorScheme.onBackground,
                onSurface = colorScheme.onSurface,
                onError = colorScheme.onError
            )
        }
    } else {
        lightColorPalette = LightColorPalette
        darkColorPalette = DarkColorPalette
    }

    val colors = if (darkTheme) {
        darkColorPalette
    } else {
        lightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = AppIdTypography,
        shapes = Shapes
    ) {
        ProvideWindowInsets {
            CompositionLocalProvider(
                LocalIndication provides rememberAppIdIndication()
            ) {
                content()
            }
        }
    }
}