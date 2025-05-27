package kz.btokmyrza.anygram.feature.country.chooser.presentation.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface CountryChooserUiModel {

    @Serializable
    data class Country(
        val flagEmoji: String,
        val countryLetterCode: String,
        val nativeName: String,
        val englishName: String,
        val callingCodes: List<String>,
    ) : CountryChooserUiModel

    @Serializable
    data class AlphabetDivider(
        val firstLetterId: String,
    ) : CountryChooserUiModel
}
