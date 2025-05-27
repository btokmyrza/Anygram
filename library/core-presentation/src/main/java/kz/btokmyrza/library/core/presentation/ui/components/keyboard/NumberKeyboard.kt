package kz.btokmyrza.library.core.presentation.ui.components.keyboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

private val keyboardRows = listOf(
    listOf("1" to "", "2" to "ABC", "3" to "DEF"),
    listOf("4" to "GHI", "5" to "JKL", "6" to "MNO"),
    listOf("7" to "PQRS", "8" to "TUV", "9" to "WXYZ"),
    listOf("0" to "+"),
)

@Composable
fun NumberKeyboard(
    modifier: Modifier = Modifier,
    onKeyPress: (String) -> Unit,
    onBackspace: () -> Unit,
) {
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        keyboardRows.take(3).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                row.forEach { (digit, letters) ->
                    DialKeyButton(
                        modifier = Modifier.weight(1f),
                        digit = digit,
                        letters = letters,
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            onKeyPress(digit)
                        },
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Spacer(modifier = Modifier.weight(1f))

            DialKeyButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                digit = "0",
                letters = "+",
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onKeyPress("0")
                },
            )

            DialIconButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onBackspace()
                },
            )
        }
    }
}

@Composable
private fun DialKeyButton(
    modifier: Modifier = Modifier,
    digit: String,
    letters: String = "",
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AnygramTheme.colors.surface,
            contentColor = AnygramTheme.colors.textPrimary,
        ),
        onClick = onClick,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = digit,
                style = AnygramTheme.fontStyles.headlineSmall.copy(
                    fontWeight = FontWeight.Normal,
                ),
            )

            Spacer(modifier = Modifier.weight(1f))

            if (letters.isNotEmpty()) {
                Text(
                    text = letters,
                    style = AnygramTheme.fontStyles.bodySmall.copy(color = Color.Gray),
                )
            }
        }
    }
}

@Composable
private fun DialIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AnygramTheme.colors.surface,
            contentColor = AnygramTheme.colors.textPrimary,
        ),
        onClick = onClick,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
        )
    }
}

@Preview
@Composable
private fun NumberKeyboardPreview() {
    AnygramTheme {
        NumberKeyboard(
            onKeyPress = {},
            onBackspace = {},
        )
    }
}
