package kz.btokmyrza.anygram.feature.phone.number.presentation.widgets

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kz.btokmyrza.anygram.feature.phone.number.R
import kz.btokmyrza.anygram.feature.phone.number.domain.model.CountryEntity
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

@Composable
internal fun PhoneNumberCountrySelector(
    modifier: Modifier = Modifier,
    selectedCountry: CountryEntity?,
    onChooseCountryClicked: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collectLatest { interaction ->
            if (interaction is PressInteraction.Release) {
                onChooseCountryClicked()
            }
        }
    }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
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
        enabled = true,
        readOnly = true,
        shape = RoundedCornerShape(16.dp),
        interactionSource = interactionSource,
    )
}

@Preview(showBackground = true)
@Composable
private fun PhoneNumberCountrySelectorPreview() {
    AnygramTheme {
        PhoneNumberCountrySelector(
            modifier = Modifier.padding(8.dp),
            selectedCountry = CountryEntity(
                flagEmoji = "🇨🇿",
                countryLetterCode = "+420",
                nativeName = "Česká republika",
                englishName = "Czech Republic",
                callingCodes = listOf("420"),
            ),
            onChooseCountryClicked = {},
        )
    }
}
