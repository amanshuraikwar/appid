package io.github.amanshuraikwar.appid.appgroupdetail

import io.github.amanshuraikwar.appid.model.AppGroup

sealed class AppGroupDetailState {
    enum class AppDisplayType {
        GRID, LIST
    }

    object Loading : AppGroupDetailState()

    data class AppGroupNotFound(
        val id: String
    ) : AppGroupDetailState()

    sealed class Success(
        val appGroup: AppGroup,
        val appDisplayType: AppDisplayType,
    ) : AppGroupDetailState() {
        class Idle(
            appGroup: AppGroup,
            appDisplayType: AppDisplayType
        ) : Success(appGroup = appGroup, appDisplayType) {
            fun copy(appDisplayType: AppDisplayType): Idle {
                return Idle(appGroup = appGroup, appDisplayType = appDisplayType)
            }
        }

        class DeletionInProgress(
            appGroup: AppGroup,
            appDisplayType: AppDisplayType,
            val progress: Float,
            val progressText: String,
        ) : Success(appGroup = appGroup, appDisplayType = appDisplayType) {
            fun copy(appDisplayType: AppDisplayType): DeletionInProgress {
                return DeletionInProgress(
                    appGroup = appGroup,
                    appDisplayType = appDisplayType,
                    progress = progress,
                    progressText = progressText
                )
            }
        }
    }
}