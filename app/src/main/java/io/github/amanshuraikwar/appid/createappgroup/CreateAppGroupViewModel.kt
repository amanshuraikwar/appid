package io.github.amanshuraikwar.appid.createappgroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.amanshuraikwar.appid.CoroutinesDispatcherProvider
import io.github.amanshuraikwar.appid.data.AppIdRepository
import io.github.amanshuraikwar.appid.model.App
import io.github.amanshuraikwar.appid.ui.UiError
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class CreateAppGroupViewModel @Inject constructor(
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val appIdRepository: AppIdRepository,
) : ViewModel() {
    private val _appList = MutableStateFlow<List<App>>(emptyList())
    val appList: StateFlow<List<App>> = _appList

    private val _selectApps = MutableStateFlow(false)
    val selectApps: StateFlow<Boolean> = _selectApps

    private val _error = MutableSharedFlow<UiError?>(
        extraBufferCapacity = 2,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val error: SharedFlow<UiError?> = _error

    private val _close = MutableStateFlow(false)
    val close: StateFlow<Boolean> = _close

    fun onAppsSelected(appList: List<App>) {
        viewModelScope.launch {
            _selectApps.value = false
            _appList.value = appList
        }
    }

    fun onAppsSelectedBackClick() {
        viewModelScope.launch {
            _selectApps.value = false
        }
    }

    fun onSelectAppsClick() {
        viewModelScope.launch {
            _selectApps.value = true
        }
    }

    fun cleanup() {
        _appList.value = emptyList()
        _selectApps.value = false
        _close.value = false
    }

    fun onCreateAppGroupClick(appGroupName: String) {
        viewModelScope.launch {
            val apps = appList.value
            when {
                appGroupName.isEmpty() -> {
                    withContext(dispatcherProvider.main) {
                        _error.tryEmit(null)
                        _error.tryEmit(UiError("App group name cannot be empty."))
                        _error.tryEmit(null)
                    }
                }
                apps.isEmpty() -> {
                    withContext(dispatcherProvider.main) {
                        _error.tryEmit(null)
                        _error.tryEmit(UiError("There are no selected apps."))
                        _error.tryEmit(null)
                    }
                }
                else -> {
                    val created = appIdRepository.createAppGroup(
                        name = appGroupName,
                        apps = apps
                    )

                    if (created) {
                        _close.value = true
                    } else {
                        withContext(dispatcherProvider.main) {
                            _error.tryEmit(null)
                            _error.tryEmit(UiError("Could not create app group."))
                            _error.tryEmit(null)
                        }
                    }
                }
            }
        }
    }

    fun onClearAppsClick() {
        _appList.value = emptyList()
    }
}