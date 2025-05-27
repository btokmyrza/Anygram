package kz.btokmyrza.library.core.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalAnygramColors = staticCompositionLocalOf<AnygramColors> {
    error("No colors provided")
}

val LocalAnygramFontStyles = staticCompositionLocalOf<AnygramFontStyles> {
    error("No fonts provided")
}

object AnygramTheme {

    val colors: AnygramColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAnygramColors.current

    val fontStyles: AnygramFontStyles
        @Composable
        @ReadOnlyComposable
        get() = LocalAnygramFontStyles.current
}

@Composable
fun AnygramTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkAnygramColors
    } else {
        LightAnygramColors
    }
    val fontStyles = DefaultAnygramFontStyles

    CompositionLocalProvider(
        LocalAnygramColors provides colors,
        LocalAnygramFontStyles provides fontStyles,
    ) {
        MaterialTheme(
            colorScheme = toM3ColorScheme(anygramColors = colors, isDark = darkTheme),
            typography = toM3Typography(fontStyles),
            content = content
        )
    }
}
