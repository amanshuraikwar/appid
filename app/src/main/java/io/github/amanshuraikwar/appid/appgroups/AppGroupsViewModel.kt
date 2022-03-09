package io.github.amanshuraikwar.appid.appgroups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.amanshuraikwar.appid.CoroutinesDispatcherProvider
import io.github.amanshuraikwar.appid.data.AppIdRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class AppGroupsViewModel @Inject constructor(
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val appIdRepository: AppIdRepository,
) : ViewModel() {
    private val _state: MutableStateFlow<AppGroupsState> =
        MutableStateFlow(AppGroupsState.Fetching)
    val state: StateFlow<AppGroupsState> = _state

    init {
        init()
    }

    private fun init() {
        viewModelScope.launch(dispatcherProvider.computation) {
            appIdRepository
                .getSavedAppGroups()
                .collect { appGroupList ->
                    withContext(dispatcherProvider.main) {
                        if (appGroupList.isEmpty()) {
                            _state.value = AppGroupsState.Empty
                        } else {
                            _state.value = AppGroupsState.Success(
                                appGroupList = appGroupList
                            )
                        }
                    }
                }
        }
    }

    fun onDeleteAppGroupClick(id: String) {
        viewModelScope.launch {
            appIdRepository.deleteAppGroup(id)
        }
    }
}