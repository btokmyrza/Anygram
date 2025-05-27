package kz.btokmyrza.anygram.feature.otp.presentation.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kz.btokmyrza.anygram.feature.otp.presentation.model.OtpInputStateUiModel
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

@Composable
internal fun OtpInput(
    modifier: Modifier = Modifier,
    totalCode: ImmutableList<String?>,
    codeLength: Int,
    focusedIndex: Int,
    inputState: OtpInputStateUiModel,
    onCellClick: (Int) -> Unit,
) {
    val focusRequesters = remember {
        List(codeLength) { FocusRequester() }
    }

    LaunchedEffect(focusedIndex) {
        if (focusedIndex in 0 until codeLength) {
            focusRequesters[focusedIndex].requestFocus()
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterHorizontally,
        ),
    ) {
        repeat(codeLength) { index ->
            val codeChar = totalCode.getOrNull(index)
            val isFocused = index == focusedIndex

            OtpInputCell(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(0.8f)
                    .focusRequester(focusRequesters[index]),
                code = codeChar,
                inputState = inputState,
                isFocused = isFocused,
                onClick = { onCellClick(index) },
            )
        }
    }
}

@Composable
private fun OtpInputCell(
    modifier: Modifier = Modifier,
    code: String?,
    inputState: OtpInputStateUiModel,
    isFocused: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = when (inputState) {
                    OtpInputStateUiModel.Default -> {
                        if (isFocused) {
                            AnygramTheme.colors.primary
                        } else {
                            AnygramTheme.colors.onSurface.copy(alpha = 0.25f)
                        }
                    }
                    OtpInputStateUiModel.Error -> {
                        AnygramTheme.colors.error
                    }
                    OtpInputStateUiModel.Success -> {
                        AnygramTheme.colors.success
                    }
                },
                shape = RoundedCornerShape(16.dp),
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedVisibility(
            visible = code != null,
            enter = slideInVertically(
                animationSpec = tween(),
                initialOffsetY = { fullHeight -> fullHeight },
            ),
            exit = shrinkOut(
                animationSpec = tween(durationMillis = 300),
                shrinkTowards = Alignment.Center,
            ) + fadeOut(animationSpec = tween(durationMillis = 200)),
        ) {
            code ?: return@AnimatedVisibility

            Text(
                text = code.toString(),
                style = AnygramTheme.fontStyles.headlineLarge,
                color = AnygramTheme.colors.textPrimary,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun OtpInputPreview() {
    var otpCode = remember { mutableStateListOf("1", "2") }
    val codeLength = 5
    var focusedIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(otpCode) {
        focusedIndex = otpCode.size.coerceAtMost(codeLength - 1)
    }

    AnygramTheme {
        OtpInput(
            totalCode = otpCode.toPersistentList(),
            codeLength = codeLength,
            focusedIndex = focusedIndex,
            inputState = OtpInputStateUiModel.Default,
            onCellClick = { index ->
                focusedIndex = index
            },
        )
    }
}
