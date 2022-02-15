package io.github.amanshuraikwar.appid.addappgroup

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

private const val TAG = "HomeViewModel"

@HiltViewModel
internal class AddAppGroupViewModel @Inject constructor(
    private val appIdRepository: AppIdRepository
) : ViewModel() {
    private val _state: MutableStateFlow<AddAppGroupState> =
        MutableStateFlow(AddAppGroupState.Loading)
    val state: StateFlow<AddAppGroupState> = _state

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
                                AddAppGroupState.NoApps(
                                    packageName = query,
                                )
                            } else {
                                AddAppGroupState.Success(
                                    packageName = query,
                                    apps = apps,
                                    canCreateAppGroup = when {
                                        query.isEmpty() -> {
                                            AddAppGroupState.CanCreateAppGroup.No(
                                                "Search for a package name " +
                                                        "to create an app group."
                                            )
                                        }
                                        apps.size > 20 -> {
                                            AddAppGroupState.CanCreateAppGroup.No(
                                                "We cannot create an app group " +
                                                        "of more than 20 apps."
                                            )
                                        }
                                        else -> {
                                            AddAppGroupState.CanCreateAppGroup.Yes
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