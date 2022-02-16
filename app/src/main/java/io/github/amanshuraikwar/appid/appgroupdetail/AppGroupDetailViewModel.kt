package io.github.amanshuraikwar.appid.appgroupdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.amanshuraikwar.appid.CoroutinesDispatcherProvider
import io.github.amanshuraikwar.appid.data.AppIdRepository
import io.github.amanshuraikwar.appid.data.AppUninstaller
import io.github.amanshuraikwar.appid.model.AppGroup
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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

    fun init(id: String) {
        viewModelScope.launch(dispatcherProvider.computation) {
            val appGroup = appIdRepository.getAppGroup(id = id)
            if (appGroup == null) {
                _state.value = AppGroupDetailState.AppGroupNotFound(
                    id = id
                )
            } else {
                _state.value = AppGroupDetailState.Success(
                    appGroup = appGroup
                )
            }
        }
    }

    fun onDeleteClick(appGroup: AppGroup) {
        viewModelScope.launch(dispatcherProvider.computation) {
//            appIdRepository.deleteApp(
//                packageName = appGroup.apps[0].packageName
//            )

//            val result = AppUninstaller.uninstall(appGroup.apps[0].packageName)
            //Log.d(TAG, "onDeleteClick: $result")
        }
    }
}