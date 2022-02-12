package io.github.amanshuraikwar.appid.home

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
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appIdRepository: AppIdRepository
) : ViewModel() {
    private val _state: MutableStateFlow<HomeViewState> = MutableStateFlow(HomeViewState.Loading)
    val state: StateFlow<HomeViewState> = _state

    private val _searchFlow = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val searchFlow: SharedFlow<String> = _searchFlow

    init {
        viewModelScope.launch(Dispatchers.Default) {
            _searchFlow
                .collect { query ->
                    val apps = appIdRepository.getAllInstalledApps(query)

                    withContext(Dispatchers.Main.immediate) {
                        _state.emit(
                            if (apps.isEmpty()) {
                                HomeViewState.NoApps
                            } else {
                                HomeViewState.Success(
                                    apps = apps
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
            _searchFlow.tryEmit(query)
        }
    }
}