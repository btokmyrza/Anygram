package kz.btokmyrza.anygram.feature.auth.start.presentation

import androidx.activity.ComponentActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kz.btokmyrza.anygram.feature.auth.start.R
import kz.btokmyrza.anygram.feature.auth.start.presentation.component.AuthStartComponent
import kz.btokmyrza.anygram.feature.auth.start.presentation.mvi.AuthStartIntent
import kz.btokmyrza.library.core.presentation.ui.components.animations.CircularReveal
import kz.btokmyrza.library.core.presentation.ui.components.animations.FlippingThemeToggleIcon
import kz.btokmyrza.library.core.presentation.ui.components.buttons.ShimmerButton
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

@Composable
fun AuthStartScreen(component: AuthStartComponent) {
    val uiState by component.uiState.collectAsStateWithLifecycle()

    AuthStartContent(
        isDarkModeEnabled = uiState.isDarkModeEnabled,
        isStartMessagingEnabled = uiState.isStartMessagingEnabled,
        onDarkModeToggled = {
            component.onIntent(AuthStartIntent.OnDarkModeToggled)
        },
        onStartMessagingClicked = {
            component.onIntent(AuthStartIntent.OnStartMessagingClicked)
        },
    )
}

@Composable
private fun AuthStartContent(
    isDarkModeEnabled: Boolean,
    isStartMessagingEnabled: Boolean,
    onDarkModeToggled: () -> Unit,
    onStartMessagingClicked: () -> Unit,
) {
    var hasAnimationFinished by remember { mutableStateOf(false) }
    val background = AnygramTheme.colors.background
    val oppositeBackground = AnygramTheme.colors.oppositeBackground
    var appliedNavBarColor by remember { mutableStateOf(background) }
    var appliedLightNavBarIcons by remember { mutableStateOf(!isDarkModeEnabled) }

    val animationDuration = 600
    val view = LocalView.current

    LaunchedEffect(hasAnimationFinished, isDarkModeEnabled) {
        if (isDarkModeEnabled) {
            delay((animationDuration * 0.6f).toLong())
        }

        val window = (view.context as? ComponentActivity)?.window ?: return@LaunchedEffect
        window.navigationBarColor = appliedNavBarColor.toArgb()
        WindowCompat.getInsetsController(window, view)
            .isAppearanceLightNavigationBars = appliedLightNavBarIcons

        window.setBackgroundDrawable(null)
        window.decorView.setBackgroundColor(background.toArgb())
    }

    CircularReveal(
        expanded = isDarkModeEnabled,
        onAnimationFinished = {
            appliedNavBarColor = oppositeBackground
            appliedLightNavBarIcons = isDarkModeEnabled
        },
        animationSpec = tween(animationDuration),
    ) { isDark ->
        AnygramTheme(darkTheme = isDark) {
            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .padding(vertical = 64.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                    ) {
                        FlippingThemeToggleIcon(
                            isDarkTheme = isDarkModeEnabled,
                            onToggle = {
                                onDarkModeToggled()
                                hasAnimationFinished = !hasAnimationFinished
                            },
                        )
                    }
                },
                bottomBar = {
                    AuthStartBottomBar(
                        modifier = Modifier.padding(32.dp),
                        isStartMessagingEnabled = isStartMessagingEnabled,
                        onStartMessagingClicked = onStartMessagingClicked,
                    )
                },
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        modifier = Modifier.size(128.dp),
                        painter = painterResource(R.drawable.ic_anygram_logo),
                        tint = AnygramTheme.colors.primary,
                        contentDescription = null,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.auth_start_app_name),
                        style = AnygramTheme.fontStyles.titleLarge,
                    )
                }
            }
        }
    }
}

@Composable
private fun AuthStartBottomBar(
    modifier: Modifier = Modifier,
    isStartMessagingEnabled: Boolean,
    onStartMessagingClicked: () -> Unit,
) {
    var shouldShrink by remember { mutableStateOf(false) }

    val scaleX by animateFloatAsState(
        targetValue = if (shouldShrink) {
            0.2f
        } else {
            1f
        },
        animationSpec = tween(durationMillis = 300),
        label = "shrinkAnimation",
    )

    Column(modifier = modifier) {
        ShimmerButton(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    this.scaleX = scaleX
                    transformOrigin = TransformOrigin(pivotFractionX = 1f, pivotFractionY = 0.5f)
                },
            text = stringResource(R.string.auth_start_button),
            enabled = isStartMessagingEnabled,
            onClick = {
                shouldShrink = true
                onStartMessagingClicked()
            },
        )

        Spacer(modifier = Modifier.height(72.dp))
    }
}

@Preview
@Composable
private fun AuthStartScreenPreview() {
    AnygramTheme {
        AuthStartContent(
            isDarkModeEnabled = true,
            isStartMessagingEnabled = true,
            onDarkModeToggled = {},
            onStartMessagingClicked = {},
        )
    }
}
