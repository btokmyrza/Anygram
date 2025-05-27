package kz.btokmyrza.library.core.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val DefaultAnygramFontStyles = AnygramFontStyles(
    displayLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),

    // Headline styles (for prominent screen titles or section heads)
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold, // Telegram often uses slightly bolder headlines
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    ),

    // Title styles (e.g., AppBar titles, dialog titles, section titles)
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium, // Telegram AppBar titles are often Medium weight
        fontSize = 22.sp,               // Common size for AppBar titles
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,               // Good for settings sections or dialog titles
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,               // Suitable for sender names in chat list, subheadings
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),

    // Body styles (main content text)
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,               // Chat messages often use a slightly larger body size
        lineHeight = 25.sp,             // Adjusted line height for readability in messages
        letterSpacing = 0.25.sp,        // Slightly increased letter spacing from 0.5 for better flow
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,               // Secondary text, message input fields
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,               // Timestamps, captions, helper text
        lineHeight = 18.sp,
        letterSpacing = 0.4.sp,
    ),

    // Label styles (for buttons, tags, and other interactive elements)
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,               // Button text
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,               // Smaller buttons, tags
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium, // Often slightly emphasized
        fontSize = 11.sp,               // Tiny helper text, "Edited" tag, status indicators
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    )
)

fun toM3Typography(anygramFontStyles: AnygramFontStyles): Typography =
    Typography(
        displayLarge = anygramFontStyles.displayLarge,
        displayMedium = anygramFontStyles.displayMedium,
        displaySmall = anygramFontStyles.displaySmall,
        headlineLarge = anygramFontStyles.headlineLarge,
        headlineMedium = anygramFontStyles.headlineMedium,
        headlineSmall = anygramFontStyles.headlineSmall,
        titleLarge = anygramFontStyles.titleLarge,
        titleMedium = anygramFontStyles.titleMedium,
        titleSmall = anygramFontStyles.titleSmall,
        bodyLarge = anygramFontStyles.bodyLarge,
        bodyMedium = anygramFontStyles.bodyMedium,
        bodySmall = anygramFontStyles.bodySmall,
        labelLarge = anygramFontStyles.labelLarge,
        labelMedium = anygramFontStyles.labelMedium,
        labelSmall = anygramFontStyles.labelSmall,
    )
