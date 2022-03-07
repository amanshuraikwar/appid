package io.github.amanshuraikwar.appid.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.ref.WeakReference
import java.util.*

class AppUninstaller(
    activity: ComponentActivity,
) {
    private val activityWr = WeakReference(activity)

    suspend fun uninstall(packageName: String): UninstallResult {
        return activityWr
            .get()
            ?.let { activity ->
                if (uninstall(activity = activity, packageName = packageName)) {
                    UninstallResult.Success
                } else {
                    UninstallResult.Failed
                }
            }
            ?: UninstallResult.ActivityGced
    }

    companion object {
        private val resultLauncherMap =
            WeakHashMap<ComponentActivity, ActivityResultLauncher<String>>()
        private val continuationMap =
            WeakHashMap<ComponentActivity, CancellableContinuation<Boolean>>()

        fun register(activity: ComponentActivity) {
            val componentActivityWr = WeakReference(activity)
            val resultLauncher = activity.registerForActivityResult(
                object : ActivityResultContract<String, Data>() {
                    override fun createIntent(context: Context, input: String): Intent {
                        return Intent(
                            Intent.ACTION_DELETE,
                            Uri.fromParts(
                                "package",
                                input,
                                null
                            )
                        ).putExtra(Intent.EXTRA_RETURN_RESULT, true)
                    }

                    override fun parseResult(resultCode: Int, intent: Intent?): Data {
                        return Data(
                            componentActivityWr = componentActivityWr,
                            result = resultCode == ComponentActivity.RESULT_OK
                        )
                    }
                }
            ) { result: Data ->
                result.componentActivityWr.get()?.let {
                    continuationMap[it]?.resumeWith(Result.success(result.result))
                    continuationMap[it] = null
                }
            }

            resultLauncherMap[activity] = resultLauncher
        }

        private suspend fun uninstall(activity: ComponentActivity, packageName: String): Boolean {
            if (continuationMap[activity] != null) return false
            return suspendCancellableCoroutine {
                continuationMap[activity] = it
                resultLauncherMap[activity]?.launch(packageName)
            }
        }
    }

    private data class Data(
        val componentActivityWr: WeakReference<ComponentActivity>,
        val result: Boolean
    )

    sealed class UninstallResult {
        object ActivityGced : UninstallResult()
        object Success : UninstallResult()
        object Failed : UninstallResult()
    }
}

@Composable
fun rememberAppUninstaller(): AppUninstaller {
    val activity = LocalContext.current as ComponentActivity
    return remember {
        AppUninstaller(activity = activity)
    }
}