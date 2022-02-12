package io.github.amanshuraikwar.appid.home

import io.github.amanshuraikwar.appid.model.App

sealed class HomeViewState {
    object Loading : HomeViewState()

    object NoApps : HomeViewState()

    data class Success(
        val apps: List<App>
    ) : HomeViewState()
}