package io.github.amanshuraikwar.appid.selectappspackage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.amanshuraikwar.appid.CoroutinesDispatcherProvider
import io.github.amanshuraikwar.appid.data.AppIdRepository
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
        MutableStateFlow<SelectAppsPackageState.Success?>(null)
    val selectAppsFlow: StateFlow<SelectAppsPackageState.Success?> = _selectAppsFlow

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

                    withContext(Dispatchers.Main.immediate) {
                        _state.emit(
                            if (apps.isEmpty()) {
                                SelectAppsPackageState.NoApps(
                                    packageName = query,
                                )
                            } else {
                                SelectAppsPackageState.Success(
                                    packageName = query,
                                    apps = apps,
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
                        _error.tryEmit(UiError("There are no apps to select."))
                        _error.tryEmit(null)
                    }
                }
                is SelectAppsPackageState.Success -> {
                    when {
                        currentState.apps.size > 20 -> {
                            withContext(dispatcherProvider.main) {
                                _error.tryEmit(null)
                                _error.tryEmit(UiError("Please select less than 20 apps."))
                                _error.tryEmit(null)
                            }
                        }
                        else -> {
                            _selectAppsFlow.emit(currentState.copy())
                        }
                    }
                }
            }
        }
    }

    fun onDispose() {
        _selectAppsFlow.value = null
        _error.tryEmit(null)
    }
}