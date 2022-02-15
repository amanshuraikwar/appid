package io.github.amanshuraikwar.appid.home

internal sealed class HomeViewState {
    object AppGroups : HomeViewState()

    object AddAppGroup : HomeViewState()

    data class AppGroup(
        val packageName: String,
    ) : HomeViewState()
}