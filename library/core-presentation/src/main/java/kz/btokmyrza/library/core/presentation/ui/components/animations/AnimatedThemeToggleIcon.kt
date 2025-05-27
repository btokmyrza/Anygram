package kz.btokmyrza.library.core.presentation.ui.components.animations

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.btokmyrza.anygram.library.core.presentation.R
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedThemeToggleIcon(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onToggle: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = onToggle,
    ) {
        AnimatedContent(
            targetState = isDarkTheme,
            label = "ThemeIconAnimation",
            transitionSpec = {
                // Define transitions:
                // Content enters by fading in and scaling up.
                val animIn = fadeIn(animationSpec = tween(250, delayMillis = 100)) +
                        scaleIn(initialScale = 0.7f, animationSpec = tween(350))
                // Content exits by fading out and scaling down.
                val animOut = fadeOut(animationSpec = tween(200)) +
                        scaleOut(targetScale = 0.7f, animationSpec = tween(250))
                // Ensure that the size does not jump during transition.
                val sizeTransform = SizeTransform(clip = false)

                animIn.togetherWith(animOut).using(sizeTransform)
            }
        ) { targetDarkTheme ->
            if (targetDarkTheme) {
                Icon(
                    painter = painterResource(R.drawable.ic_dark_mode),
                    contentDescription = "Switch to Light Theme",
                    modifier = Modifier.size(32.dp),
                    tint = AnygramTheme.colors.primary,
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.ic_light_mode),
                    contentDescription = "Switch to Dark Theme",
                    modifier = Modifier.size(32.dp),
                    tint = AnygramTheme.colors.primary,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AnimatedThemeToggleIconPreview() {
    var isDark by remember { mutableStateOf(false) }

    AnygramTheme {
        AnimatedThemeToggleIcon(
            isDarkTheme = isDark,
            onToggle = { isDark = !isDark },
        )
    }
}
