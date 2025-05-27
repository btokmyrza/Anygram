package kz.btokmyrza.library.core.presentation.ui.components.buttons

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

@Composable
fun ShimmerButton(
    modifier: Modifier = Modifier,
    text: String,
    shimmerColor: Color = Color.White,
    shimmerActiveDurationMillis: Int = 800,
    shimmerPauseMillis: Int = 2000,
    shimmerBeamWidthFactor: Float = 1.0f,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    var componentWidth by remember { mutableFloatStateOf(0f) }
    var componentHeight by remember { mutableFloatStateOf(0f) }

    val totalCycleDuration = shimmerActiveDurationMillis + shimmerPauseMillis
    val activeRatio = shimmerActiveDurationMillis.toFloat() / totalCycleDuration

    val infiniteTransition = rememberInfiniteTransition(label = "shimmer_button_transition")
    val overallProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = totalCycleDuration,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmer_overall_progress",
    )

    Button(
        modifier = modifier
            .onSizeChanged { intSize ->
                componentWidth = intSize.width.toFloat()
                componentHeight = intSize.height.toFloat()
            }
            .clip(RoundedCornerShape(8.dp))
            .drawWithContent {
                drawContent()

                if (componentWidth > 0f && componentHeight > 0f && overallProgress <= activeRatio) {
                    val activeProgress = overallProgress / activeRatio
                    val stripWidth = componentHeight * shimmerBeamWidthFactor
                    val totalTravelDistance = componentWidth + stripWidth
                    val currentXOffset = -stripWidth + (activeProgress * totalTravelDistance)
                    val beamColors = listOf(
                        Color.Transparent,
                        shimmerColor.copy(alpha = 0.0f),
                        shimmerColor.copy(alpha = 0.6f),
                        shimmerColor.copy(alpha = 0.0f),
                        Color.Transparent,
                    )
                    val brush = Brush.linearGradient(
                        colors = beamColors,
                        start = Offset(x = currentXOffset, y = 0f),
                        end = Offset(x = currentXOffset + stripWidth, y = componentHeight),
                    )

                    drawRect(
                        brush = brush,
                        topLeft = Offset.Zero,
                        size = size,
                    )
                }
            },
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(16.dp),
        enabled = enabled,
        onClick = onClick,
    ) {
        Text(
            text = text,
            color = AnygramTheme.colors.buttonText
        )
    }
}

@Preview
@Composable
private fun ShimmerButtonPreview() {
    AnygramTheme {
        ShimmerButton(
            modifier = Modifier
                .padding(vertical = 64.dp)
                .padding(horizontal = 32.dp)
                .fillMaxWidth(),
            text = "Start messaging",
            enabled = true,
            onClick = {},
        )
    }
}
