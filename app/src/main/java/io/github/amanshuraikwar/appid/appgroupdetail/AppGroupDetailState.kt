package io.github.amanshuraikwar.appid.appgroupdetail

import io.github.amanshuraikwar.appid.model.AppGroup

sealed class AppGroupDetailState {
    object Loading : AppGroupDetailState()

    data class AppGroupNotFound(
        val id: String
    ) : AppGroupDetailState()

    data class Success(
        val appGroup: AppGroup
    ) : AppGroupDetailState()
}