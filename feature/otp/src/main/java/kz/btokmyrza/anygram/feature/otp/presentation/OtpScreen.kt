package kz.btokmyrza.anygram.feature.otp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import kz.btokmyrza.anygram.feature.otp.presentation.component.OtpComponent
import kz.btokmyrza.anygram.feature.otp.presentation.mvi.OtpIntent
import kz.btokmyrza.anygram.feature.otp.presentation.mvi.OtpUiState
import kz.btokmyrza.anygram.feature.otp.presentation.widget.OtpInput
import kz.btokmyrza.library.core.presentation.ui.components.keyboard.NumberKeyboard
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

@Composable
fun OtpScreen(component: OtpComponent) {
    val uiState by component.uiState.collectAsStateWithLifecycle()

    OtpScreenContent(
        uiState = uiState,
        onIntent = component::onIntent,
    )
}

@Composable
private fun OtpScreenContent(
    uiState: OtpUiState,
    onIntent: (OtpIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        OtpInput(
            totalCode = uiState.totalCode,
            codeLength = uiState.codeLength,
            focusedIndex = uiState.focusedIndex,
            onCellClick = { index ->
                onIntent(OtpIntent.OnFieldFocused(index))
            },
        )

        Spacer(modifier = Modifier.weight(1f))

        NumberKeyboard(
            onKeyPress = { digit ->
                onIntent(OtpIntent.OnCodeChanged(code = digit))
            },
            onBackspace = {

            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OtpScreenPreview() {
    val stubUiState = OtpUiState(
        totalCode = persistentListOf("1", "2"),
        codeLength = 5,
        focusedIndex = 0,
    )

    AnygramTheme {
        OtpScreenContent(
            uiState = stubUiState,
            onIntent = {},
        )
    }
}
