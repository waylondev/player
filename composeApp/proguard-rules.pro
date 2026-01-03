# ProGuard rules for Android optimization

# Keep Kotlin metadata for reflection
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

# Keep Compose runtime classes
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.foundation.** { *; }
-keep class androidx.compose.material3.** { *; }
-keep class androidx.compose.ui.** { *; }

# Keep ExoPlayer classes
-keep class androidx.media3.** { *; }

# Keep Ktor client classes
-keep class io.ktor.client.** { *; }
-keep class io.ktor.network.** { *; }

# Keep serialization classes
-keep class kotlinx.serialization.** { *; }

# Keep model classes
-keep class dev.waylon.player.model.** { *; }

# Keep service classes
-keep class dev.waylon.player.service.** { *; }

# Keep UI components
-keep class dev.waylon.player.ui.** { *; }

# Remove debug logging
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# Optimize the code
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification

# Remove unused code
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose