package io.github.amanshuraikwar.appid.appgroupdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.amanshuraikwar.appid.CoroutinesDispatcherProvider
import io.github.amanshuraikwar.appid.data.AppIdRepository
import io.github.amanshuraikwar.appid.data.AppUninstaller
import io.github.amanshuraikwar.appid.model.AppGroup
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "AppGroupDetailViewModel"

@HiltViewModel
internal class AppGroupDetailViewModel @Inject constructor(
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val appIdRepository: AppIdRepository
) : ViewModel() {
    private val _state: MutableStateFlow<AppGroupDetailState> =
        MutableStateFlow(AppGroupDetailState.Loading)
    val state: StateFlow<AppGroupDetailState> = _state

    private val _backClick = MutableSharedFlow<Boolean>()
    val backClick: SharedFlow<Boolean> = _backClick

    fun init(id: String) {
        viewModelScope.launch(dispatcherProvider.computation) {
            val appGroup = appIdRepository.getAppGroup(id = id)
            if (appGroup == null) {
                _state.value = AppGroupDetailState.AppGroupNotFound(
                    id = id
                )
            } else {
                _state.value = AppGroupDetailState.Success.Idle(
                    appGroup = appGroup,
                    appDisplayType = AppGroupDetailState.AppDisplayType.GRID
                )
            }
        }
    }

    fun onDeleteClick(appGroup: AppGroup, appUninstaller: AppUninstaller) {
        viewModelScope.launch(dispatcherProvider.computation) {
            val appDisplayType = withContext(dispatcherProvider.main) {
                (_state.value as? AppGroupDetailState.Success)?.appDisplayType
            } ?: return@launch

            var modifiedAppGroup = appGroup
            appGroup.apps.forEachIndexed { index, app ->
                if (isActive) {
                    withContext(dispatcherProvider.main) {
                        _state.value = AppGroupDetailState.Success.DeletionInProgress(
                            appGroup = modifiedAppGroup,
                            progress = index.toFloat() / (appGroup.apps.size.toFloat()),
                            progressText = "Deleting ${index + 1} of ${appGroup.apps.size} apps...",
                            appDisplayType = appDisplayType
                        )
                    }

                    val uninstallResult = appUninstaller.uninstall(app.packageName)

                    if (uninstallResult == AppUninstaller.UninstallResult.Success) {
                        modifiedAppGroup = modifiedAppGroup.copy(
                            apps = modifiedAppGroup.apps.filter {
                                it.packageName != app.packageName
                            }
                        )
                        appIdRepository.appWasUninstalled(app.packageName)
                    }
                    Log.d(TAG, "onDeleteClick: $uninstallResult")
                }
            }

            withContext(dispatcherProvider.main) {
                _state.value = AppGroupDetailState.Success.Idle(
                    appGroup = modifiedAppGroup,
                    appDisplayType = appDisplayType,
                )
            }

            if (modifiedAppGroup.apps.isEmpty()) {
                _backClick.emit(true)
            }
        }
    }

    fun onGridViewClick() {
        viewModelScope.launch(dispatcherProvider.main) {
            val currentState = _state.value
            if (currentState is AppGroupDetailState.Success.Idle) {
                _state.value =
                    currentState.copy(appDisplayType = AppGroupDetailState.AppDisplayType.GRID)
            }
            if (currentState is AppGroupDetailState.Success.DeletionInProgress) {
                _state.value =
                    currentState.copy(appDisplayType = AppGroupDetailState.AppDisplayType.GRID)
            }
        }
    }

    fun onListViewClick() {
        viewModelScope.launch(dispatcherProvider.main) {
            val currentState = _state.value
            if (currentState is AppGroupDetailState.Success.Idle) {
                _state.value =
                    currentState.copy(appDisplayType = AppGroupDetailState.AppDisplayType.LIST)
            }
            if (currentState is AppGroupDetailState.Success.DeletionInProgress) {
                _state.value =
                    currentState.copy(appDisplayType = AppGroupDetailState.AppDisplayType.LIST)
            }
        }
    }
}