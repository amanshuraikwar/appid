package io.github.amanshuraikwar.appid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import io.github.amanshuraikwar.appid.home.HomeView
import io.github.amanshuraikwar.appid.ui.theme.AppIdTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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