package kz.btokmyrza.anygram.feature.country.chooser.presentation.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.btokmyrza.anygram.feature.country.chooser.presentation.model.CountryChooserUiModel
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

@Composable
internal fun CountryCodeItem(
    modifier: Modifier = Modifier,
    country: CountryChooserUiModel.Country,
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = country.flagEmoji)

            Spacer(modifier = Modifier.width(16.dp))

            Text(text = country.englishName)
        }

        Text(
            text = country.callingCodes.joinToString(separator = ", ") { "+$it" },
            color = AnygramTheme.colors.primary,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CountryCodeItemPreview() {
    AnygramTheme {
        CountryCodeItem(
            country = CountryChooserUiModel.Country(
                flagEmoji = "🇰🇿",
                countryLetterCode = "KZ",
                nativeName = "Kazakhstan",
                englishName = "Kazakhstan",
                callingCodes = listOf("7", "77"),
            ),
        )
    }
}
