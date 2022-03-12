package io.github.amanshuraikwar.appid.home

internal sealed class HomeViewState {
    object AppGroups : HomeViewState()

    object CreateAppGroup : HomeViewState()

    object About : HomeViewState()

    data class AppGroupDetail(
        val id: String,
    ) : HomeViewState()
}