package kz.btokmyrza.library.core.presentation.ui.components.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.btokmyrza.anygram.library.core.presentation.R
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

@Composable
fun FlippingThemeToggleIcon(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onToggle: () -> Unit,
) {
    // Animatable for the rotation around the Y axis
    // Moon is considered at 0 degrees (front)
    // Sun is considered at 180 degrees (effectively the "back" side, which flips to front)
    val rotationYAnim = remember {
        Animatable(initialValue = getTargetValue(isDarkTheme))
    }

    LaunchedEffect(isDarkTheme) {
        rotationYAnim.animateTo(
            targetValue = getTargetValue(isDarkTheme),
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        )
    }

    // Determine which icon is "visible" based on the rotation angle
    // The icon displayed changes when it passes the 90-degree mark of its flip.
    val showMoon =
        (rotationYAnim.value >= 0f && rotationYAnim.value < 90f) ||
                (rotationYAnim.value > 270f && rotationYAnim.value <= 360f)
    // If target is Moon (isDarkTheme = true), it rotates towards 0.
    // If target is Sun (isDarkTheme = false), it rotates towards 180.
    // The actual icon displayed should be the one that will be visible when the animation settles.
    val currentIcon = if (isDarkTheme) {
        painterResource(R.drawable.ic_dark_mode)
    } else {
        painterResource(R.drawable.ic_light_mode)
    }
    val contentDescription = if (isDarkTheme) {
        "Switch to Light Theme"
    } else {
        "Switch to Dark Theme"
    }

    val density = LocalDensity.current

    IconButton(
        modifier = modifier,
        onClick = onToggle,
    ) {
        Icon(
            modifier = Modifier
                .size(32.dp)
                .graphicsLayer {
                    // Apply the rotation
                    rotationY = rotationYAnim.value
                    // Add cameraDistance for a more pronounced 3D effect
                    cameraDistance = 8f * density.density
                },
            painter = currentIcon,
            contentDescription = contentDescription,
            tint = AnygramTheme.colors.primary,
        )
    }
}

private fun getTargetValue(isDarkTheme: Boolean): Float =
    if (isDarkTheme) {
        0f
    } else {
        180f
    }

@Preview(showBackground = true)
@Composable
fun FlippingThemeToggleIconPreview() {
    var isDark by remember { mutableStateOf(false) }

    AnygramTheme {
        FlippingThemeToggleIcon(isDarkTheme = isDark, onToggle = { isDark = !isDark })
    }
}
