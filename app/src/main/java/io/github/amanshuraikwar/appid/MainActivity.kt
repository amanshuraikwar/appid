package io.github.amanshuraikwar.appid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import io.github.amanshuraikwar.appid.home.HomeView
import io.github.amanshuraikwar.appid.ui.theme.AppIdTheme
import io.github.amanshuraikwar.appid.util.AppUninstaller

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    init {
        AppUninstaller.register(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSystemBars()

        setContent {
            AppIdTheme {
                HomeView()
            }
        }
    }
}