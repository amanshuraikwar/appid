package io.github.amanshuraikwar.appid.appgroups

import io.github.amanshuraikwar.appid.model.AppGroup

internal sealed class AppGroupsState {
    object Fetching : AppGroupsState()
    object Empty : AppGroupsState()
    data class Success(
        val appGroupList: List<AppGroup>
    ) : AppGroupsState()
}