package io.github.amanshuraikwar.appid.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@HiltViewModel
internal class HomeViewModel @Inject constructor() : ViewModel() {
    private val _state: MutableStateFlow<HomeViewState> =
        MutableStateFlow(HomeViewState.AppGroups)
    val state: StateFlow<HomeViewState> = _state

    fun onBackClick() {
        _state.value = HomeViewState.AppGroups
    }

    fun onCreateAppGroupClick() {
        _state.value = HomeViewState.AddAppGroup
    }
}