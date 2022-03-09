package io.github.amanshuraikwar.appid.util

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import io.github.amanshuraikwar.appid.model.App
import java.lang.ref.WeakReference

class AppLauncher(
    activity: ComponentActivity,
) {
    private val activityWr = WeakReference(activity)

    fun launch(app: App): Boolean {
        return runCatching {
            activityWr
                .get()
                ?.let { activity ->
                    activity.startActivity(
                        activity.packageManager.getLaunchIntentForPackage(app.packageName)
                    )
                    true
                }
                ?: false
        }.getOrNull() ?: false
    }
}

@Composable
fun rememberAppLauncher(): AppLauncher {
    val activity = LocalContext.current as ComponentActivity
    return remember {
        AppLauncher(activity)
    }
}