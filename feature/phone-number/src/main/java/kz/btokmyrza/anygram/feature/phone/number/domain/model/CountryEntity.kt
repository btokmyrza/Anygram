package kz.btokmyrza.anygram.feature.phone.number.domain.model

data class CountryEntity(
    val flagEmoji: String,
    val countryLetterCode: String,
    val nativeName: String,
    val englishName: String,
    val callingCodes: List<String>,
)
