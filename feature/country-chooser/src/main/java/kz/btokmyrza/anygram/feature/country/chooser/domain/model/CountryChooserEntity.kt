package kz.btokmyrza.anygram.feature.country.chooser.domain.model

data class CountryChooserEntity(
    val flagEmoji: String,
    val countryLetterCode: String,
    val nativeName: String,
    val englishName: String,
    val callingCodes: List<String>,
)
