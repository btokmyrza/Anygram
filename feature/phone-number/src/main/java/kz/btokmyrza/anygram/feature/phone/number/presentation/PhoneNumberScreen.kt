package kz.btokmyrza.anygram.feature.phone.number.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import kz.btokmyrza.anygram.feature.phone.number.presentation.widgets.PhoneNumberErrorAlertDialog
import kz.btokmyrza.anygram.feature.phone.number.presentation.widgets.PhoneNumberInput
import kz.btokmyrza.anygram.feature.phone.number.presentation.widgets.PhoneNumberKeyboard
import kz.btokmyrza.library.core.presentation.ui.components.buttons.ProgressFloatingActionButton
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

@Composable
fun PhoneNumberScreen(component: PhoneNumberComponent) {
    val uiState by component.uiState.collectAsStateWithLifecycle()

    PhoneNumberContent(
        selectedCountry = uiState.selectedCountry,
        activeField = uiState.activeField,
        countryCode = uiState.phoneCode,
        phoneNumber = uiState.phoneNumber,
        phoneNumberMask = uiState.phoneNumberMask,
        errorMessage = uiState.errorMessage,
        isLoading = uiState.isLoading,
        onIntent = component::onIntent,
    )
}

@Composable
private fun PhoneNumberContent(
    selectedCountry: CountryEntity?,
    activeField: ActiveField,
    countryCode: String,
    phoneNumber: String,
    phoneNumberMask: String,
    errorMessage: String?,
    isLoading: Boolean,
    onIntent: (PhoneNumberIntent) -> Unit,
) {
    var isKeyboardVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isKeyboardVisible = true
    }

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        content = { paddingValues ->
            PhoneNumberContent(
                paddingValues = paddingValues,
                selectedCountry = selectedCountry,
                activeField = activeField,
                phoneCode = countryCode,
                phoneNumber = phoneNumber,
                phoneNumberMask = phoneNumberMask,
                onIntent = onIntent,
            )
        },
        bottomBar = {
            PhoneNumberBottomBar(
                isKeyboardVisible = isKeyboardVisible,
                activeField = activeField,
                isLoading = isLoading,
                onNextClicked = { onIntent(PhoneNumberIntent.OnNextClicked) },
                onCountryCodeChanged = { countryCode ->
                    onIntent(PhoneNumberIntent.OnPhoneCodeChanged(countryCode))
                },
                onPhoneNumberChanged = { phoneNumber ->
                    onIntent(PhoneNumberIntent.OnPhoneNumberChanged(phoneNumber))
                },
                onCountryCodeDeleted = {
                    onIntent(PhoneNumberIntent.OnPhoneCodeDeleted)
                },
                onPhoneNumberDeleted = {
                    onIntent(PhoneNumberIntent.OnPhoneNumberDeleted)
                },
            )
        },
    )

    if (errorMessage != null) {
        PhoneNumberErrorAlertDialog(
            errorMessage = errorMessage,
            onDismissRequest = { onIntent(PhoneNumberIntent.OnOkClicked) },
        )
    }
}

@Composable
private fun PhoneNumberContent(
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

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onIntent(PhoneNumberIntent.OnChooseCountryClicked) },
            value = selectedCountry?.englishName.orEmpty(),
            onValueChange = {},
            leadingIcon = selectedCountry?.flagEmoji
                ?.takeIf { flagEmoji -> flagEmoji.isNotBlank() }
                ?.let { flagEmoji ->
                    { Text(text = flagEmoji) }
                },
            trailingIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(
                        id = R.string.phone_number_country_chooser_content_description,
                    ),
                )
            },
            label = { Text(text = stringResource(R.string.phone_number_country_chooser_label)) },
            enabled = false,
            readOnly = true,
            shape = RoundedCornerShape(16.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        PhoneNumberInput(
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
    activeField: ActiveField,
    isLoading: Boolean,
    onNextClicked: () -> Unit,
    onCountryCodeChanged: (String) -> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    onCountryCodeDeleted: () -> Unit,
    onPhoneNumberDeleted: () -> Unit,
) {
    AnimatedVisibility(
        visible = isKeyboardVisible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 600),
        ) { fullHeight -> fullHeight } + fadeIn(
            animationSpec = tween(durationMillis = 600),
        ),
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
                onClick = onNextClicked,
            )

            Spacer(modifier = Modifier.height(16.dp))

            PhoneNumberKeyboard(
                onKeyPress = { number ->
                    when (activeField) {
                        ActiveField.PHONE_CODE -> onCountryCodeChanged(number)
                        ActiveField.PHONE_NUMBER -> onPhoneNumberChanged(number)
                    }
                },
                onBackspace = {
                    when (activeField) {
                        ActiveField.PHONE_CODE -> onCountryCodeDeleted()
                        ActiveField.PHONE_NUMBER -> onPhoneNumberDeleted()
                    }
                },
            )
        }
    }
}

@Preview
@Composable
private fun PhoneNumberScreenPreview() {
    AnygramTheme {
        PhoneNumberContent(
            selectedCountry = null,
            activeField = ActiveField.PHONE_CODE,
            countryCode = "7",
            phoneNumber = "",
            phoneNumberMask = "### ### ####",
            errorMessage = null,
            isLoading = false,
            onIntent = {},
        )
    }
}
