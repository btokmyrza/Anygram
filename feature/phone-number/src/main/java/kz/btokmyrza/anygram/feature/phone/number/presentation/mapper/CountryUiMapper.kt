package kz.btokmyrza.anygram.feature.phone.number.presentation.mapper

import kz.btokmyrza.anygram.feature.country.chooser.domain.model.CountryChooserEntity
import kz.btokmyrza.anygram.feature.country.chooser.presentation.model.CountryChooserUiModel
import kz.btokmyrza.anygram.feature.phone.number.domain.model.CountryEntity

class CountryUiMapper {

    fun toCountryChooserEntityList(
        countryEntities: List<CountryEntity>,
    ): List<CountryChooserEntity> =
        countryEntities
            .sortedBy { countryEntity ->
                countryEntity.englishName.lowercase()
            }
            .map { countryEntity ->
                CountryChooserEntity(
                    flagEmoji = countryEntity.flagEmoji,
                    countryLetterCode = countryEntity.countryLetterCode,
                    nativeName = countryEntity.nativeName,
                    englishName = countryEntity.englishName,
                    callingCodes = countryEntity.callingCodes,
                )
            }

    fun toCountryEntity(countryChooserUiModel: CountryChooserUiModel.Country): CountryEntity =
        CountryEntity(
            flagEmoji = countryChooserUiModel.flagEmoji,
            countryLetterCode = countryChooserUiModel.countryLetterCode,
            nativeName = countryChooserUiModel.nativeName,
            englishName = countryChooserUiModel.englishName,
            callingCodes = countryChooserUiModel.callingCodes,
        )
}
