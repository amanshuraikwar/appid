package io.github.amanshuraikwar.appid.addappgroup

import io.github.amanshuraikwar.appid.model.App

internal sealed class AddAppGroupState {
    object Loading : AddAppGroupState()

    data class NoApps(
        val packageName: String,
    ) : AddAppGroupState()

    data class Success(
        val packageName: String,
        val canCreateAppGroup: CanCreateAppGroup,
        val apps: List<App>
    ) : AddAppGroupState()

    sealed class CanCreateAppGroup {
        object Yes : CanCreateAppGroup()
        data class No(val why: String) : CanCreateAppGroup()
    }
}