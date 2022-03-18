@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.github.amanshuraikwar.appid.buildSrc

object Libs {
    const val compileSdk = 32
    const val minSdk = 23
    const val targetSdk = 32

    const val androidGradlePlugin = "com.android.tools.build:gradle:7.3.0-alpha06"

    object Kotlin {
        const val version = "1.6.10"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object KotlinX {
        const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.3.1"
        const val serializationCore = "org.jetbrains.kotlinx:kotlinx-serialization-core:1.2.1"
        const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1"
        const val serializationGradlePlugin = "org.jetbrains.kotlin:kotlin-serialization:1.5.10"
    }

    object Coroutines {
        private const val version = "1.6.0-native-mt"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        // TODO-amanshuraikwar (02 Nov 2021 04:09:19 PM): do not use this in KMM common module
        //  this is JVM only dependency for now
        //  Ref: https://github.com/Kotlin/kotlinx.coroutines/issues/1996
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object AndroidX {
        object Work {
            private const val version = "2.7.1"
            const val runtime = "androidx.work:work-runtime:$version"
            const val ktx = "androidx.work:work-runtime-ktx:$version"
        }

        object Navigation {
            private const val version = "2.3.3"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }

        const val coreKtx = "androidx.core:core-ktx:1.7.0"

        object Lifecycle {
            private const val version = "2.2.0"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        }

        object Room {
            private const val version = "2.4.0"
            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
        }

        object Compose {
            const val version = "1.2.0-alpha05"
            const val ui = "androidx.compose.ui:ui:$version"
            const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
            const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val material = "androidx.compose.material:material:$version"
            const val materialIcons = "androidx.compose.material:material-icons-core:$version"
            const val materialIconsExtended =
                "androidx.compose.material:material-icons-extended:$version"
            const val activity = "androidx.activity:activity-compose:1.4.0"
            const val material3 = "androidx.compose.material3:material3:1.0.0-alpha04"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0-alpha01"

            val all = listOf(
                ui,
                uiTooling,
                foundation,
                material,
                materialIcons,
                materialIconsExtended,
                activity,
            )
        }
    }

    object DaggerHilt {
        private const val version = "2.38.1"
        const val library = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
    }

    object Retrofit {
        private const val version = "2.8.1"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$version"
    }

    object OkHttp {
        private const val version = "4.7.2"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Accompanist {
        const val insets = "com.google.accompanist:accompanist-insets:0.24.3-alpha"
    }

    object SqlDelight {
        private const val version = "1.5.3"
        const val gradlePlugin = "com.squareup.sqldelight:gradle-plugin:$version"
        const val androidDriver = "com.squareup.sqldelight:android-driver:$version"
        const val nativeDriver = "com.squareup.sqldelight:native-driver:$version"
        const val jvmDriver = "com.squareup.sqldelight:sqlite-driver:$version"
    }

    object Ktor {
        private const val version = "1.6.3"
        const val clientCore = "io.ktor:ktor-client-core:$version"
        const val clientJson = "io.ktor:ktor-client-json:$version"
        const val clientLogging = "io.ktor:ktor-client-logging:$version"
        const val clientSerialization = "io.ktor:ktor-client-serialization:$version"
        const val clientAndroid = "io.ktor:ktor-client-android:$version"
        const val clientApache = "io.ktor:ktor-client-apache:$version}"
        const val clientIos = "io.ktor:ktor-client-ios:$version"
        const val clientCio = "io.ktor:ktor-client-cio:$version"
        const val clientJs = "io.ktor:ktor-client-js:$version"
        const val clientMock = "io.ktor:ktor-client-mock:$version"
    }

    object Coil {
        const val compose = "io.coil-kt:coil-compose:2.0.0-alpha08"
    }

    object Google {
        const val gms = "com.google.gms:google-services:4.3.10"
        const val crashlyticsPlugin = "com.google.firebase:firebase-crashlytics-gradle:2.8.1"
        const val analytics = "com.google.firebase:firebase-analytics:20.0.2"
        const val crashlytics = "com.google.firebase:firebase-crashlytics:18.2.6"
    }
}
