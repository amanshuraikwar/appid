package io.github.amanshuraikwar.appid.home

internal sealed class HomeViewState {
    object AppGroups : HomeViewState()

    object CreateAppGroup : HomeViewState()

    data class AppGroupDetail(
        val id: String,
    ) : HomeViewState()
}