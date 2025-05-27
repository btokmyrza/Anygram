package kz.btokmyrza.anygram.feature.phone.number.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kz.btokmyrza.anygram.feature.phone.number.R
import kz.btokmyrza.anygram.feature.phone.number.domain.model.CountryEntity
import kz.btokmyrza.anygram.feature.phone.number.presentation.component.PhoneNumberComponent
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.ActiveField
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberIntent
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberUiState
import kz.btokmyrza.anygram.feature.phone.number.presentation.widgets.PhoneNumberCountrySelector
import kz.btokmyrza.anygram.feature.phone.number.presentation.widgets.PhoneNumberErrorAlertDialog
import kz.btokmyrza.anygram.feature.phone.number.presentation.widgets.PhoneNumberInput
import kz.btokmyrza.library.core.presentation.ui.components.buttons.ProgressFloatingActionButton
import kz.btokmyrza.library.core.presentation.ui.components.keyboard.NumberKeyboard
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

@Composable
fun PhoneNumberScreen(component: PhoneNumberComponent) {
    val uiState by component.uiState.collectAsStateWithLifecycle()

    PhoneNumberContent(
        uiState = uiState,
        onIntent = component::onIntent,
    )
}

@Composable
private fun PhoneNumberContent(
    uiState: PhoneNumberUiState,
    onIntent: (PhoneNumberIntent) -> Unit,
) {
    var isKeyboardVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isKeyboardVisible = true
    }

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        content = { paddingValues ->
            PhoneNumberMainContent(
                paddingValues = paddingValues,
                selectedCountry = uiState.selectedCountry,
                activeField = uiState.activeField,
                phoneCode = uiState.phoneCode,
                phoneNumber = uiState.phoneNumber,
                phoneNumberMask = uiState.phoneNumberMask,
                onIntent = onIntent,
            )
        },
        bottomBar = {
            PhoneNumberBottomBar(
                isKeyboardVisible = isKeyboardVisible,
                isLoading = uiState.isLoading,
                onIntent = onIntent,
            )
        },
    )

    if (uiState.errorMessage != null) {
        PhoneNumberErrorAlertDialog(
            errorMessage = uiState.errorMessage,
            onDismissRequest = { onIntent(PhoneNumberIntent.OnOkClicked) },
        )
    }
}

@Composable
private fun PhoneNumberMainContent(
    paddingValues: PaddingValues,
    selectedCountry: CountryEntity?,
    activeField: ActiveField,
    phoneCode: String,
    phoneNumber: String,
    phoneNumberMask: String,
    onIntent: (PhoneNumberIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.phone_number_title),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.phone_number_subtitle),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(32.dp))

        PhoneNumberCountrySelector(
            selectedCountry = selectedCountry,
            onChooseCountryClicked = { onIntent(PhoneNumberIntent.OnChooseCountryClicked) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        PhoneNumberInput(
            modifier = Modifier.defaultMinSize(minHeight = 56.dp),
            activeField = activeField,
            phoneCode = phoneCode,
            phoneNumber = phoneNumber,
            phoneNumberMask = phoneNumberMask,
            onActiveFieldChanged = { activeField ->
                onIntent(PhoneNumberIntent.OnActiveFieldChanged(activeField))
            },
        )
    }
}

@Composable
private fun PhoneNumberBottomBar(
    isKeyboardVisible: Boolean,
    isLoading: Boolean,
    onIntent: (PhoneNumberIntent) -> Unit,
) {
    AnimatedVisibility(
        visible = isKeyboardVisible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 600),
            initialOffsetY = { fullHeight -> fullHeight },
        ) + fadeIn(animationSpec = tween(durationMillis = 600)),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.End,
        ) {
            ProgressFloatingActionButton(
                isLoading = isLoading,
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        tint = AnygramTheme.colors.onPrimary,
                        contentDescription = null,
                    )
                },
                onClick = { onIntent(PhoneNumberIntent.OnNextClicked) },
            )

            Spacer(modifier = Modifier.height(16.dp))

            NumberKeyboard(
                onKeyPress = { number ->
                    onIntent(PhoneNumberIntent.OnPhoneChanged(number))
                },
                onBackspace = {
                    onIntent(PhoneNumberIntent.OnPhoneDeleted)
                },
            )
        }
    }
}

@Preview
@Composable
private fun PhoneNumberScreenPreview() {
    val uiState = PhoneNumberUiState(
        selectedCountry = CountryEntity(
            flagEmoji = "🇨🇿",
            countryLetterCode = "+420",
            nativeName = "Česká republika",
            englishName = "Czech Republic",
            callingCodes = listOf("420"),
        ),
        activeField = ActiveField.PHONE_CODE,
        phoneCode = "420",
        phoneNumber = "",
        phoneNumberMask = "### ### ###",
        phoneNumberMaxLength = 9,
        errorMessage = null,
        isLoading = false,
    )

    AnygramTheme {
        PhoneNumberContent(
            uiState = uiState,
            onIntent = {},
        )
    }
}
