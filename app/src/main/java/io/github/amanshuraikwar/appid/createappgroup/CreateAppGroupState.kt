package io.github.amanshuraikwar.appid.createappgroup

import io.github.amanshuraikwar.appid.model.App

internal sealed class CreateAppGroupState {
    object Loading : CreateAppGroupState()

    data class NoApps(
        val packageName: String,
    ) : CreateAppGroupState()

    data class Success(
        val packageName: String,
        val canCreateAppGroup: CanCreateAppGroup,
        val apps: List<App>
    ) : CreateAppGroupState()

    sealed class CanCreateAppGroup {
        object Yes : CanCreateAppGroup()
        data class No(val why: String) : CanCreateAppGroup()
    }
}