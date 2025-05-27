package kz.btokmyrza.anygram.feature.phone.number.presentation.widgets

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.btokmyrza.anygram.feature.phone.number.R
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.ActiveField
import kz.btokmyrza.library.core.presentation.ui.components.keyboard.DisableSoftKeyboard
import kz.btokmyrza.library.core.presentation.ui.components.transformations.PhoneNumberVisualTransformation
import kz.btokmyrza.library.core.presentation.ui.components.transformations.PrefixVisualTransformation
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberInput(
    modifier: Modifier = Modifier,
    activeField: ActiveField,
    phoneCode: String,
    phoneNumber: String,
    phoneNumberMask: String,
    label: String = stringResource(R.string.phone_number_field_label),
    maxCountryCodeLength: Int = 4,
    textStyle: TextStyle = LocalTextStyle.current,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = AnygramTheme.colors.textPrimary,
        unfocusedTextColor = AnygramTheme.colors.textPrimary,
        disabledTextColor = AnygramTheme.colors.textPrimary,
        focusedLabelColor = AnygramTheme.colors.primary,
        unfocusedLabelColor = AnygramTheme.colors.primary,
        disabledLabelColor = AnygramTheme.colors.primary,
        focusedBorderColor = AnygramTheme.colors.primary,
        unfocusedBorderColor = AnygramTheme.colors.primary,
        disabledBorderColor = AnygramTheme.colors.primary,
    ),
    onActiveFieldChanged: (ActiveField) -> Unit,
) {
    val phoneCodeFocusRequester = remember { FocusRequester() }
    val phoneNumberFocusRequester = remember { FocusRequester() }

    var phoneCodeValue by remember {
        mutableStateOf(TextFieldValue(phoneCode))
    }
    var phoneNumberValue by remember {
        mutableStateOf(TextFieldValue(phoneNumber))
    }

    LaunchedEffect(activeField) {
        when (activeField) {
            ActiveField.PHONE_CODE -> {
                phoneCodeFocusRequester.requestFocus()
            }
            ActiveField.PHONE_NUMBER -> {
                phoneNumberFocusRequester.requestFocus()
            }
        }
    }

    LaunchedEffect(phoneCode) {
        if (phoneCode != phoneCodeValue.text) {
            phoneCodeValue = TextFieldValue(
                text = phoneCode,
                selection = TextRange(index = phoneCode.length),
            )
        }
    }

    LaunchedEffect(phoneNumber) {
        if (phoneNumber != phoneNumberValue.text) {
            phoneNumberValue = TextFieldValue(
                text = phoneNumber,
                selection = TextRange(index = phoneNumber.length),
            )
        }
    }

    DisableSoftKeyboard {
        Box(modifier = modifier) {
            OutlinedTextFieldDefaults.DecorationBox(
                value = "+",
                container = {
                    OutlinedTextFieldDefaults.Container(
                        enabled = true,
                        isError = false,
                        interactionSource = interactionSource,
                        colors = colors,
                        shape = RoundedCornerShape(16.dp),
                        focusedBorderThickness = 2.dp,
                        unfocusedBorderThickness = 2.dp,
                    )
                },
                label = { Text(text = label) },
                innerTextField = {
                    PhoneNumberInputContent(
                        countryCodeFocusRequester = phoneCodeFocusRequester,
                        phoneNumberFocusRequester = phoneNumberFocusRequester,
                        textStyle = textStyle.copy(color = AnygramTheme.colors.textPrimary),
                        maxCountryCodeLength = maxCountryCodeLength,
                        countryCodeValue = phoneCodeValue,
                        phoneNumberValue = phoneNumberValue,
                        phoneNumberMask = phoneNumberMask,
                        onActiveFieldChanged = onActiveFieldChanged,
                    )
                },
                enabled = true,
                isError = false,
                interactionSource = interactionSource,
                colors = colors,
                placeholder = null,
                supportingText = null,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
            )
        }
    }
}

@Composable
private fun PhoneNumberInputContent(
    countryCodeFocusRequester: FocusRequester,
    phoneNumberFocusRequester: FocusRequester,
    textStyle: TextStyle,
    maxCountryCodeLength: Int,
    countryCodeValue: TextFieldValue,
    phoneNumberValue: TextFieldValue,
    phoneNumberMask: String,
    onActiveFieldChanged: (ActiveField) -> Unit,
) {
    val placeholder = remember(phoneNumberMask) {
        phoneNumberMask.replace("#", "0")
    }
    val visualTransformation = remember(phoneNumberMask) {
        if (phoneNumberMask.isNotBlank()) {
            PhoneNumberVisualTransformation(mask = phoneNumberMask)
        } else {
            VisualTransformation.None
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            modifier = Modifier
                .focusRequester(countryCodeFocusRequester)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        onActiveFieldChanged(ActiveField.PHONE_CODE)
                    }
                }
                .width((maxCountryCodeLength * 12).dp + 8.dp)
                .padding(end = 4.dp),
            value = countryCodeValue,
            visualTransformation = PrefixVisualTransformation(prefix = "+"),
            cursorBrush = SolidColor(AnygramTheme.colors.primary),
            textStyle = textStyle,
            singleLine = true,
            interactionSource = remember { MutableInteractionSource() },
            onValueChange = {},
        )

        VerticalDivider(modifier = Modifier.height(24.dp))

        BasicTextField(
            modifier = Modifier
                .focusRequester(phoneNumberFocusRequester)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        onActiveFieldChanged(ActiveField.PHONE_NUMBER)
                    }
                }
                .weight(1f)
                .padding(start = 8.dp),
            value = phoneNumberValue,
            visualTransformation = visualTransformation,
            cursorBrush = SolidColor(AnygramTheme.colors.primary),
            textStyle = textStyle,
            singleLine = true,
            interactionSource = remember { MutableInteractionSource() },
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (phoneNumberValue.text.isBlank()) {
                        Text(
                            text = placeholder,
                            color = AnygramTheme.colors.textSecondary,
                        )
                    }
                    innerTextField()
                }
            },
            onValueChange = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthPhoneNumberInputPreview() {
    AnygramTheme {
        var previewCountryCode by remember { mutableStateOf("7") }
        var previewPhoneNumber by remember { mutableStateOf("") }

        PhoneNumberInput(
            modifier = Modifier.padding(16.dp),
            activeField = ActiveField.PHONE_CODE,
            phoneCode = previewCountryCode,
            phoneNumber = previewPhoneNumber,
            phoneNumberMask = "### ### ####",
            onActiveFieldChanged = {},
        )
    }
}
