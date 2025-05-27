package kz.btokmyrza.library.core.presentation.ui.components.animations

import android.graphics.Path
import android.view.MotionEvent
import androidx.annotation.FloatRange
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.focusable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme
import kotlin.math.hypot

@Composable
fun CircularReveal(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    animationSpec: FiniteAnimationSpec<Float> = tween(),
    onAnimationFinished: () -> Unit = {},
    content: @Composable (targetState: Boolean) -> Unit,
) {
    val transition = updateTransition(
        targetState = expanded,
        label = "Circular reveal",
    )

    LaunchedEffect(transition.isRunning, transition.currentState, transition.targetState) {
        if (!transition.isRunning && transition.currentState == transition.targetState) {
            onAnimationFinished()
        }
    }

    val initialState = remember { expanded }

    transition.CircularReveal(
        modifier = modifier,
        initialState = initialState,
        animationSpec = animationSpec,
        content = content,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Transition<Boolean>.CircularReveal(
    modifier: Modifier = Modifier,
    initialState: Boolean,
    animationSpec: FiniteAnimationSpec<Float> = tween(),
    content: @Composable (targetState: Boolean) -> Unit,
) {
    var offset: Offset? by remember { mutableStateOf(null) }
    val currentlyVisible = remember {
        mutableStateListOf<Boolean>().apply { add(initialState) }
    }
    val contentMap = remember {
        mutableMapOf<Boolean, @Composable () -> Unit>()
    }
    if (currentState == targetState) {
        // If not animating, just display the current state
        if (currentlyVisible.size != 1 || currentlyVisible[0] != targetState) {
            contentMap.clear()
        }
    }
    if (!contentMap.contains(targetState)) {
        // Replace target with the same key if any
        val replacementId = currentlyVisible.indexOfFirst { visible ->
            visible == targetState
        }
        if (replacementId == -1) {
            currentlyVisible.add(targetState)
        } else {
            currentlyVisible[replacementId] = targetState
        }
        contentMap.clear()
        currentlyVisible.fastForEach { stateForContent ->
            contentMap[stateForContent] = {
                val progress by animateFloat(
                    label = "Progress",
                    transitionSpec = { animationSpec },
                ) { visible ->
                    val targetedContent =
                        stateForContent != currentlyVisible.last() || visible == stateForContent
                    if (targetedContent) {
                        1f
                    } else {
                        0f
                    }
                }

                val focusRequester = remember { FocusRequester() }
                LaunchedEffect(targetState) {
                    focusRequester.requestFocus()
                }
                Box(
                    modifier = Modifier
                        .focusable()
                        .focusRequester(focusRequester)
                        .circularReveal(progress = progress, offset = offset),
                ) {
                    content(stateForContent)
                }
            }
        }
    }
    Box(
        modifier = modifier.pointerInteropFilter { motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                if (!started) {
                    offset = Offset(x = motionEvent.x, y = motionEvent.y)
                }
            }
            started
        }
    ) {
        currentlyVisible.fastForEach { visible ->
            key(visible) {
                contentMap[visible]?.invoke()
            }
        }
    }
}

private val Transition<Boolean>.started
    get() = currentState != targetState || isRunning

private fun Modifier.circularReveal(
    @FloatRange(from = 0.0, to = 1.0)
    progress: Float,
    offset: Offset? = null,
): Modifier =
    clip(
        shape = CircularRevealShape(
            progress = progress,
            offset = offset,
        ),
    )

private class CircularRevealShape(
    @FloatRange(from = 0.0, to = 1.0)
    private val progress: Float,
    private val offset: Offset? = null,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline =
        Outline.Generic(
            path = Path().apply {
                addCircle(
                    offset?.x ?: (size.width / 2f),
                    offset?.y ?: (size.height / 2f),
                    longestDistanceToACorner(size = size, offset = offset) * progress,
                    Path.Direction.CW,
                )
            }.asComposePath(),
        )

    private fun longestDistanceToACorner(size: Size, offset: Offset?): Float {
        if (offset == null) {
            return hypot(x = size.width / 2f, y = size.height / 2f)
        }

        val topLeft = hypot(x = offset.x, y = offset.y)
        val topRight = hypot(x = size.width - offset.x, y = offset.y)
        val bottomLeft = hypot(x = offset.x, y = size.height - offset.y)
        val bottomRight = hypot(x = size.width - offset.x, y = size.height - offset.y)

        return maxOf(topLeft, topRight, bottomLeft, bottomRight)
    }
}

@Preview
@Composable
private fun CircularRevealAnimationPreview() {
    val isSystemDark = true
    var darkTheme by remember { mutableStateOf(isSystemDark) }
    val onThemeToggle = { darkTheme = !darkTheme }

    CircularReveal(
        expanded = darkTheme,
        animationSpec = tween(1500)
    ) { isDark ->
        AnygramTheme(darkTheme = isDark) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                onClick = onThemeToggle
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(120.dp),
                        imageVector = if (!isDark) {
                            Icons.Default.PlayArrow
                        } else {
                            Icons.Default.PlayArrow
                        },
                        contentDescription = "Toggle",
                    )
                }
            }
        }
    }
}
