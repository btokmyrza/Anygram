package kz.btokmyrza.anygram.feature.otp.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import kz.btokmyrza.anygram.feature.otp.R
import kz.btokmyrza.anygram.feature.otp.presentation.component.OtpComponent
import kz.btokmyrza.anygram.feature.otp.presentation.model.OtpInputStateUiModel
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
    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(horizontal = 16.dp),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    modifier = Modifier.size(128.dp),
                    painter = painterResource(R.drawable.ic_otp),
                    tint = AnygramTheme.colors.primary,
                    contentDescription = null,
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = stringResource(
                        id = R.string.otp_title,
                        formatArgs = arrayOf(uiState.codeType),
                    ),
                    style = AnygramTheme.fontStyles.titleLarge,
                    color = AnygramTheme.colors.textPrimary,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(
                        id = R.string.otp_subtitle,
                        formatArgs = arrayOf(uiState.phoneNumber),
                    ),
                    style = AnygramTheme.fontStyles.titleSmall.copy(fontWeight = FontWeight.Normal),
                    color = AnygramTheme.colors.textPrimary,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(32.dp))

                OtpInput(
                    totalCode = uiState.totalCode,
                    codeLength = uiState.codeLength,
                    focusedIndex = uiState.focusedIndex,
                    inputState = uiState.inputState,
                    onCellClick = { index ->
                        onIntent(OtpIntent.OnFieldFocused(index))
                    },
                )
            }
        },
        bottomBar = {
            OtpScreenBottomBar(
                inputState = uiState.inputState,
                onKeyPress = { digit ->
                    onIntent(OtpIntent.OnCodeChanged(code = digit))
                },
                onBackspace = {
                    onIntent(OtpIntent.OnCodeDeleted)
                },
            )
        },
    )
}

@Composable
private fun OtpScreenBottomBar(
    modifier: Modifier = Modifier,
    inputState: OtpInputStateUiModel,
    onKeyPress: (String) -> Unit,
    onBackspace: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedContent(
            targetState = inputState,
            label = "OtpHintAnimation",
        ) { inputState ->
            when (inputState) {
                OtpInputStateUiModel.Default,
                OtpInputStateUiModel.Success -> {
                    Text(
                        text = stringResource(R.string.otp_hint_default),
                        style = AnygramTheme.fontStyles.titleSmall.copy(
                            fontWeight = FontWeight.Normal,
                        ),
                        color = AnygramTheme.colors.primary,
                        textAlign = TextAlign.Center,
                    )
                }
                OtpInputStateUiModel.Error -> {
                    Text(
                        text = stringResource(R.string.otp_hint_error),
                        style = AnygramTheme.fontStyles.titleSmall.copy(
                            fontWeight = FontWeight.Normal,
                        ),
                        color = AnygramTheme.colors.error,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        NumberKeyboard(
            onKeyPress = onKeyPress,
            onBackspace = onBackspace,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OtpScreenPreview() {
    val stubUiState = OtpUiState(
        phoneNumber = "+77771234567",
        codeType = "Telegram message",
        nextCodeType = null,
        codeResendTimeout = 0,
        totalCode = persistentListOf("1", "2"),
        codeLength = 5,
        focusedIndex = 0,
        inputState = OtpInputStateUiModel.Default,
    )

    AnygramTheme {
        OtpScreenContent(
            uiState = stubUiState,
            onIntent = {},
        )
    }
}
