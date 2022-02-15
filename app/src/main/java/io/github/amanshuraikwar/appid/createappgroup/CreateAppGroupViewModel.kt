package io.github.amanshuraikwar.appid.createappgroup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.amanshuraikwar.appid.data.AppIdRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "CreateAppGroupViewModel"

@HiltViewModel
internal class CreateAppGroupViewModel @Inject constructor(
    private val appIdRepository: AppIdRepository
) : ViewModel() {
    private val _state: MutableStateFlow<CreateAppGroupState> =
        MutableStateFlow(CreateAppGroupState.Loading)
    val state: StateFlow<CreateAppGroupState> = _state

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
                                CreateAppGroupState.NoApps(
                                    packageName = query,
                                )
                            } else {
                                CreateAppGroupState.Success(
                                    packageName = query,
                                    apps = apps,
                                    canCreateAppGroup = when {
                                        query.isEmpty() -> {
                                            CreateAppGroupState.CanCreateAppGroup.No(
                                                "Search for a package name " +
                                                        "to create an app group."
                                            )
                                        }
                                        apps.size > 20 -> {
                                            CreateAppGroupState.CanCreateAppGroup.No(
                                                "We cannot create an app group " +
                                                        "of more than 20 apps."
                                            )
                                        }
                                        else -> {
                                            CreateAppGroupState.CanCreateAppGroup.Yes
                                        }
                                    }
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

    fun onCreateGroupClick(packageName: String) {
        viewModelScope.launch {
            appIdRepository.addAppGroup(packageName)
        }
    }
}