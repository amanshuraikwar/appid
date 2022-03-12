# For stack traces
-keepattributes SourceFile, LineNumberTable

-keep public enum io.github.amanshuraikwar.appid.**{
    *;
}

-keeppackagenames io.github.amanshuraikwar.appid

-dontwarn kotlin.internal.**
-dontwarn kotlin.reflect.jvm.internal.ReflectionFactoryImpl