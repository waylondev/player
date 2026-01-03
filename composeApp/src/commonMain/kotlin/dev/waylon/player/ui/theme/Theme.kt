package dev.waylon.player.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Tech-styled theme configuration
 */

// Dark theme color scheme (default)
val DarkColorScheme = darkColorScheme(
    // Primary colors
    primary = Color(0xFF00C8FF),       // Bright blue, tech-styled main color
    primaryContainer = Color(0xFF001E2D), // Dark cyan background
    onPrimary = Color(0xFF000000),        // Black text on primary color

    // Secondary colors
    secondary = Color(0xFF00FFA3),       // Bright green, tech-styled accent color
    secondaryContainer = Color(0xFF00291C), // Dark green background
    onSecondary = Color(0xFF000000),      // Black text on secondary color

    // Background and surface
    background = Color(0xFF00141E),       // Dark blue background
    surface = Color(0xFF001A27),         // Dark cyan surface
    surfaceVariant = Color(0xFF002232),   // Deeper cyan variant
    onBackground = Color(0xFFFFFFFF),     // White text on background
    onSurface = Color(0xFFFFFFFF),       // White text on surface

    // Error colors
    error = Color(0xFFFF5252),           // Bright red error indicator
    errorContainer = Color(0xFF3D0000),   // Dark red error background
    onError = Color(0xFFFFFFFF),         // White text on error color

    // Other
    outline = Color(0xFF00C8FF),          // Bright blue border
    inverseSurface = Color(0xFF00C8FF),   // Bright blue inverse surface
    inverseOnSurface = Color(0xFF000000),  // Black text on inverse surface
    inversePrimary = Color(0xFF0099CC),    // Dark blue inverse primary

    // Light scheme (used in light mode)
    surfaceTint = Color(0xFF00C8FF),      // Bright blue surface tint
    outlineVariant = Color(0xFF006680),    // Dark blue border variant
    scrim = Color(0xFF000000),            // Black scrim
)

// Light theme color scheme (alternative)
val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006680),       // Dark blue primary color
    primaryContainer = Color(0xFFB3E5FC), // Light cyan background
    onPrimary = Color(0xFFFFFFFF),      // White text on primary color

    secondary = Color(0xFF00796B),       // Dark green secondary color
    secondaryContainer = Color(0xFFB2DFDB), // Light green background
    onSecondary = Color(0xFFFFFFFF),      // White text on secondary color

    background = Color(0xFFF5F5F5),       // Light gray background
    surface = Color(0xFFFFFFFF),         // White surface
    onBackground = Color(0xFF000000),     // Black text on background
    onSurface = Color(0xFF000000),       // Black text on surface

    error = Color(0xFFD32F2F),           // Dark red error indicator
    errorContainer = Color(0xFFFFEBEE),   // Light red error background
    onError = Color(0xFFFFFFFF),         // White text on error color

    outline = Color(0xFF006680),          // Dark blue border
    inverseSurface = Color(0xFF006680),   // Dark blue inverse surface
    inverseOnSurface = Color(0xFFFFFFFF),  // White text on inverse surface
    inversePrimary = Color(0xFF00C8FF),    // Bright blue inverse primary

    surfaceTint = Color(0xFF006680),      // Dark blue surface tint
    outlineVariant = Color(0xFFB3E5FC),    // Light cyan border variant
    scrim = Color(0xFF000000),            // Black scrim
)

// Tech-styled typography configuration
object Typography {
    // Large title
    val largeTitle = androidx.compose.ui.text.TextStyle(
        fontSize = 32.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        letterSpacing = 0.5.sp
    )

    // Title
    val title = androidx.compose.ui.text.TextStyle(
        fontSize = 24.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
        letterSpacing = 0.sp
    )

    // Subtitle
    val subtitle = androidx.compose.ui.text.TextStyle(
        fontSize = 18.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
        letterSpacing = 0.15.sp
    )

    // Body
    val body = androidx.compose.ui.text.TextStyle(
        fontSize = 16.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
        letterSpacing = 0.5.sp
    )

    // Small body
    val smallBody = androidx.compose.ui.text.TextStyle(
        fontSize = 14.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
        letterSpacing = 0.25.sp
    )

    // Button text
    val button = androidx.compose.ui.text.TextStyle(
        fontSize = 14.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
        letterSpacing = 1.25.sp
    )

    // Small title
    val smallTitle = androidx.compose.ui.text.TextStyle(
        fontSize = 14.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
        letterSpacing = 0.1.sp
    )
}

// Common spacing configuration
object Spacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 16.dp
    val lg = 24.dp
    val xl = 32.dp
    val xxl = 48.dp
}

// Corner radius configuration
object Corners {
    val sm = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
    val md = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
    val lg = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
    val xl = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)
    val full = androidx.compose.foundation.shape.CircleShape
}

// Elevation configuration
object Elevation {
    val sm = 2.dp
    val md = 4.dp
    val lg = 8.dp
    val xl = 16.dp
}
