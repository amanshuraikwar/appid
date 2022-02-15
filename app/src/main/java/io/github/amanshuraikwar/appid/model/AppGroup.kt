package io.github.amanshuraikwar.appid.model

data class AppGroup(
    val id: String,
    val name: String,
    val apps: List<App>
)