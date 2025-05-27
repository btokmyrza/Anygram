package kz.btokmyrza.library.core.presentation.ui.components.buttons

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

@Composable
fun ProgressFloatingActionButton(
    modifier: Modifier = Modifier,
    progressIndicatorSize: Dp = 24.dp,
    progressIndicatorStrokeWidth: Dp = 3.dp,
    isLoading: Boolean,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = modifier,
        shape = CircleShape,
        containerColor = AnygramTheme.colors.primary,
        onClick = {
            if (!isLoading) {
                onClick()
            }
        },
    ) {
        Box(contentAlignment = Alignment.Center) {
            Crossfade(
                targetState = isLoading,
                label = "fab_content_animation",
            ) { loading ->
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(progressIndicatorSize),
                        color = AnygramTheme.colors.onPrimary,
                        strokeWidth = progressIndicatorStrokeWidth,
                    )
                } else {
                    icon()
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProgressFloatingActionButtonPreview() {
    AnygramTheme {
        ProgressFloatingActionButton(
            isLoading = false,
            icon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    tint = AnygramTheme.colors.onPrimary,
                    contentDescription = null,
                )
            },
            onClick = {},
        )
    }
}
