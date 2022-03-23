package io.github.amanshuraikwar.appid.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.amanshuraikwar.appid.CoroutinesDispatcherProvider
import io.github.amanshuraikwar.appid.data.AppIdRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val appIdRepository: AppIdRepository
) : ViewModel() {
    private val _state: MutableStateFlow<HomeViewState> =
        MutableStateFlow(HomeViewState.AppGroups)
    val state: StateFlow<HomeViewState> = _state

    fun onBackClick() {
        _state.value = HomeViewState.AppGroups
    }

    fun onCreateAppGroupClick() {
        _state.value = HomeViewState.CreateAppGroup
    }

    fun onAppGroupClick(id: String) {
        _state.value = HomeViewState.AppGroupDetail(
            id = id
        )
    }

    @Suppress("UNUSED_PARAMETER")
    fun onReturnedFromAppDetails(appGroupId: String) {
        viewModelScope.launch(dispatcherProvider.computation) {
            appIdRepository.updateInstalledAppCache()
        }
    }

    fun onAboutClick() {
        _state.value = HomeViewState.About
    }

}