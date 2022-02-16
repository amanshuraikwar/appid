package io.github.amanshuraikwar.appid.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.ref.WeakReference
import java.util.*

object AppUninstaller {
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

    suspend fun uninstall(activity: ComponentActivity, packageName: String): Boolean {
        if (continuationMap[activity] != null) return false
        return suspendCancellableCoroutine {
            continuationMap[activity] = it
            resultLauncherMap[activity]?.launch(packageName)
        }
    }

    private data class Data(
        val componentActivityWr: WeakReference<ComponentActivity>,
        val result: Boolean
    )
}