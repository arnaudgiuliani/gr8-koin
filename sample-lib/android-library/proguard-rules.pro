# Obfuscate all classes in the package io.kotzilla.sample.sdk
# Keep public classes, methods, and fields that are part of the public API to maintain accessibility.
-keep class org.koin.** { *; }
-keep public class io.kotzilla.sample.sdk.** { public *; }
-repackageclasses io.kotzilla.sample.sdk
-keep class io.kotzilla.sample.sdk.** { *; }

# Keep necessary serialization components for Kotlin serialization
# Companion objects and serializer methods are required for proper serialization functionality.
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}

# Keep serializer methods on companion objects of serializable classes
-if @kotlinx.serialization.Serializable class ** {
    static **$* *;
}
-keepclassmembers class <2>$<3> {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep INSTANCE and serializer() methods of serializable objects
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# Preserve runtime-visible annotations for polymorphic serialization
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault
