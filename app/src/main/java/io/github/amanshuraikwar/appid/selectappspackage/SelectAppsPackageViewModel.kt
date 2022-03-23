package io.github.amanshuraikwar.appid.selectappspackage

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.amanshuraikwar.appid.CoroutinesDispatcherProvider
import io.github.amanshuraikwar.appid.data.AppIdRepository
import io.github.amanshuraikwar.appid.model.App
import io.github.amanshuraikwar.appid.ui.UiError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "CreateAppGroupViewModel"

@HiltViewModel
internal class SelectAppsPackageViewModel @Inject constructor(
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val appIdRepository: AppIdRepository
) : ViewModel() {
    private val _state =
        MutableStateFlow<SelectAppsPackageState>(SelectAppsPackageState.Idle)
    val state: StateFlow<SelectAppsPackageState> = _state

    private val _selectAppsFlow =
        MutableStateFlow<List<App>?>(null)
    val selectAppsFlow: StateFlow<List<App>?> = _selectAppsFlow

    private val _error = MutableSharedFlow<UiError?>(
        extraBufferCapacity = 2,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val error: SharedFlow<UiError?> = _error

    private val searchFlow = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        viewModelScope.launch(Dispatchers.Default) {
            searchFlow
                .collect { query ->
                    val apps = appIdRepository.getAllInstalledApps(query)

                    // O(M+N) algo that propagates the
                    // "T-1"th state's selection of common items to "T"th state
                    withContext(Dispatchers.Main.immediate) {
                        val currentState = _state.value
                        val currentSelectableApps: SnapshotStateList<SelectableApp> =
                            if (currentState is SelectAppsPackageState.Success) {
                                currentState.apps
                            } else {
                                SnapshotStateList()
                            }

                        var selectedAppCount = 0
                        val selectableApps = SnapshotStateList<SelectableApp>()

                        var i = 0
                        var j = 0

                        // only run until we exhaust new items
                        // rest of the old items must be discarded
                        while (i < apps.size) {
                            if (j < currentSelectableApps.size) {
                                if (apps[i] == currentSelectableApps[j].app) {
                                    // for common items keep the selected state
                                    selectableApps.add(currentSelectableApps[j])
                                    if (currentSelectableApps[j].selected) {
                                        selectedAppCount++
                                    }
                                    i++
                                    j++
                                } else if (apps[i] < currentSelectableApps[j].app) {
                                    // new ith item will never be == current jth item
                                    // so just add the new ith item as unselected
                                    selectableApps.add(
                                        SelectableApp(
                                            selected = false,
                                            app = apps[i]
                                        )
                                    )
                                    i++
                                } else {
                                    // wait for current jth item to become >= new ith item
                                    j++
                                }
                            } else {
                                // we have exhausted current items
                                selectableApps.add(
                                    SelectableApp(
                                        selected = false,
                                        app = apps[i]
                                    )
                                )
                                i++
                            }
                        }

                        _state.emit(
                            if (apps.isEmpty()) {
                                SelectAppsPackageState.NoApps(
                                    packageName = query,
                                )
                            } else {
                                SelectAppsPackageState.Success(
                                    packageName = query,
                                    apps = selectableApps,
                                    selectedAppCount = mutableStateOf(selectedAppCount)
                                )
                            }
                        )
                    }

                    delay(300)
                }
        }
    }



    fun onSearch(query: String) {
        Log.d(TAG, "onSearch: $query")
        viewModelScope.launch {
            searchFlow.tryEmit(query)
        }
    }

    fun onSelectClick() {
        viewModelScope.launch {
            when (val currentState = _state.value) {
                SelectAppsPackageState.Idle -> {
                    // do nothing
                }
                is SelectAppsPackageState.NoApps -> {
                    withContext(dispatcherProvider.main) {
                        _error.tryEmit(null)
                        _error.tryEmit(UiError("There are no selected apps."))
                        _error.tryEmit(null)
                    }
                }
                is SelectAppsPackageState.Success -> {
                    withContext(dispatcherProvider.main) {
                        when {
                            currentState.apps.count { it.selected } == 0 -> {

                                _error.tryEmit(null)
                                _error.tryEmit(UiError("There are no selected apps."))
                                _error.tryEmit(null)
                            }

                            else -> {
                                _selectAppsFlow.emit(
                                    currentState.copy()
                                        .apps
                                        .filter { it.selected }
                                        .map { it.app }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun onDispose() {
        _selectAppsFlow.value = null
        _error.tryEmit(null)
        _state.value = SelectAppsPackageState.Idle
    }

    fun onAppClick(clickedSelectableApp: SelectableApp) {
        viewModelScope.launch {
            withContext(dispatcherProvider.main) {
                val currentState = _state.value
                if (currentState is SelectAppsPackageState.Success) {
                    val index = currentState.apps.indexOfFirst { selectableApp ->
                        selectableApp.app.packageName ==
                                clickedSelectableApp.app.packageName
                    }

                    if (index == -1) {
                        return@withContext
                    }

                    val currentSelectableApp = currentState.apps[index]
                    currentState.apps[index] =
                        currentSelectableApp.copy(
                            selected = !currentSelectableApp.selected,
                        )

                    if (currentSelectableApp.selected) {
                        currentState.selectedAppCount.value -= 1
                    } else {
                        currentState.selectedAppCount.value += 1
                    }
                }
            }
        }
    }

    fun onSelectAllClick() {
        viewModelScope.launch {
            withContext(dispatcherProvider.main) {
                val currentState = _state.value
                if (currentState is SelectAppsPackageState.Success) {
                    val apps = currentState.apps

                    for (i in 0 until apps.size) {
                        apps[i] = apps[i].copy(selected = true)
                    }

                    currentState.selectedAppCount.value = currentState.apps.size
                }
            }
        }
    }

    fun onDeselectAllClick() {
        viewModelScope.launch {
            withContext(dispatcherProvider.main) {
                val currentState = _state.value
                if (currentState is SelectAppsPackageState.Success) {
                    val apps = currentState.apps

                    for (i in 0 until apps.size) {
                        apps[i] = apps[i].copy(selected = false)
                    }

                    currentState.selectedAppCount.value = 0
                }
            }
        }
    }
}