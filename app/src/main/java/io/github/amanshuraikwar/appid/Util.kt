package io.github.amanshuraikwar.appid

import android.app.Activity
import android.content.res.Configuration
import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.doOnLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

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
