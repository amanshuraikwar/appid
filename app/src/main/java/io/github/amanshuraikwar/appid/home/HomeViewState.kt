package io.github.amanshuraikwar.appid.home

internal sealed class HomeViewState {
    object AppGroups : HomeViewState()

    object CreateAppGroup : HomeViewState()

    data class AppGroup(
        val packageName: String,
    ) : HomeViewState()
}