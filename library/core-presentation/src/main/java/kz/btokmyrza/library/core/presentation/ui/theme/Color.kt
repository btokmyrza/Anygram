package kz.btokmyrza.library.core.presentation.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Telegram Light Theme Colors
val TelegramBlueLight = Color(0xFF52A5E4)
val TelegramBackgroundLight = Color(0xFFFFFFFF)
val TelegramSurfaceLight = Color(0xFFF0F4F7)
val TelegramTextLight = Color(0xFF000000)
val TelegramSecondaryTextLight = Color(0xFF8A8A8D)
val TelegramButtonTextLight = Color(0xFFFFFFFF)
val TelegramIconLight = Color(0xFFFFFFFF)
val TelegramDividerLight = Color(0xFFD1D1D6)
val TelegramSuccessLight = Color(0xFF4CAF50)
val TelegramErrorLight = Color(0xFFD32F2F)
val TelegramOutgoingBubbleLight = Color(0xFFEFFDDD)
val TelegramIncomingBubbleLight = Color(0xFFFFFFFF)

// Telegram Dark Theme Colors
val TelegramBlueDark = Color(0xFF52A5E4)
val TelegramBackgroundDark = Color(0xFF18222D)
val TelegramSurfaceDark = Color(0xFF212D3B)
val TelegramTextDark = Color(0xFFFFFFFF)
val TelegramSecondaryTextDark = Color(0xFF708499)
val TelegramButtonTextDark = Color(0xFFFFFFFF)
val TelegramIconDark = Color(0xFFFFFFFF)
val TelegramDividerDark = Color(0xFF2C3A48)
val TelegramSuccessDark = Color(0xFF4CAF50)
val TelegramErrorDark = Color(0xFFEF5350)
val TelegramOutgoingBubbleDark = Color(0xFF334756)
val TelegramIncomingBubbleDark = Color(0xFF212D3B)

val LightAnygramColors = AnygramColors(
    primary = TelegramBlueLight,
    onPrimary = TelegramIconLight,
    background = TelegramBackgroundLight,
    oppositeBackground = TelegramBackgroundDark,
    outline = TelegramDividerLight,
    surface = TelegramSurfaceLight,
    onSurface = TelegramTextLight,
    onSurfaceVariant = TelegramSecondaryTextLight,
    textPrimary = TelegramTextLight,
    textSecondary = TelegramSecondaryTextLight,
    buttonText = TelegramButtonTextLight,
    divider = TelegramDividerLight,
    success = TelegramSuccessLight,
    error = TelegramErrorLight,
    outgoingBubble = TelegramOutgoingBubbleLight,
    incomingBubble = TelegramIncomingBubbleLight,
)

val DarkAnygramColors = AnygramColors(
    primary = TelegramBlueDark,
    onPrimary = TelegramIconDark,
    background = TelegramBackgroundDark,
    oppositeBackground = TelegramBackgroundLight,
    outline = TelegramDividerDark,
    surface = TelegramSurfaceDark,
    onSurface = TelegramTextDark,
    onSurfaceVariant = TelegramSecondaryTextDark,
    textPrimary = TelegramTextDark,
    textSecondary = TelegramSecondaryTextDark,
    buttonText = TelegramButtonTextDark,
    divider = TelegramDividerDark,
    success = TelegramSuccessDark,
    error = TelegramErrorDark,
    outgoingBubble = TelegramOutgoingBubbleDark,
    incomingBubble = TelegramIncomingBubbleDark,
)

fun toM3ColorScheme(
    anygramColors: AnygramColors,
    isDark: Boolean,
): androidx.compose.material3.ColorScheme {
    val onPrimaryColor = if (isDark || anygramColors.primary.luminance() > 0.5f) {
            Color.Black
        } else {
            Color.White
        }
    val onErrorColor = if (isDark || anygramColors.error.luminance() > 0.5f) {
        Color.Black
    } else {
        Color.White
    }

    return if (isDark) {
        darkColorScheme(
            primary = anygramColors.primary,
            onPrimary = anygramColors.onPrimary,
            background = anygramColors.background,
            onBackground = anygramColors.textPrimary,
            outline = anygramColors.outline,
            surface = anygramColors.surface,
            onSurface = anygramColors.onSurface,
            error = anygramColors.error,
            onError = onErrorColor,
            secondary = anygramColors.primary,
            onSecondary = onPrimaryColor,
            surfaceVariant = anygramColors.surface,
            onSurfaceVariant = anygramColors.onSurfaceVariant,
        )
    } else {
        lightColorScheme(
            primary = anygramColors.primary,
            onPrimary = anygramColors.onPrimary,
            background = anygramColors.background,
            onBackground = anygramColors.textPrimary,
            outline = anygramColors.outline,
            surface = anygramColors.surface,
            onSurface = anygramColors.onSurface,
            error = anygramColors.error,
            onError = onErrorColor,
            secondary = anygramColors.primary,
            onSecondary = onPrimaryColor,
            surfaceVariant = anygramColors.surface,
            onSurfaceVariant = anygramColors.onSurfaceVariant,
        )
    }
}

private fun Color.luminance(): Float {
    val red = this.red
    val green = this.green
    val blue = this.blue

    return (0.2126f * red + 0.7152f * green + 0.0722f * blue)
}
