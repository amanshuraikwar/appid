package io.github.amanshuraikwar.appid.model

data class App(
    val name: String,
    val packageName: String,
    val versionName: String,
) : Comparable<App> {
    override operator fun compareTo(other: App): Int {
        val nameResult = name.compareTo(other.name)
        return if (nameResult == 0) {
            packageName.compareTo(other.packageName)
        } else {
            nameResult
        }
    }
}