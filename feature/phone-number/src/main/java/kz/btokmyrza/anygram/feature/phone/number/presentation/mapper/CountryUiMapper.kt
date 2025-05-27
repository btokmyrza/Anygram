package kz.btokmyrza.anygram.feature.phone.number.presentation.mapper

import kz.btokmyrza.anygram.feature.country.chooser.presentation.model.CountryChooserUiModel
import kz.btokmyrza.anygram.feature.phone.number.domain.model.CountryEntity

class CountryUiMapper {

    fun toCountryUiModelList(countryEntities: List<CountryEntity>): List<CountryChooserUiModel> {
        val sortedCountryEntities = countryEntities.sortedBy { countryEntity ->
            countryEntity.englishName.lowercase()
        }

        val resultList = mutableListOf<CountryChooserUiModel>()

        for (i in sortedCountryEntities.indices) {
            val currentEntity = sortedCountryEntities[i]
            val mappedCountryChooserUiModel = CountryChooserUiModel.Country(
                flagEmoji = currentEntity.flagEmoji,
                countryCode = currentEntity.countryCode,
                nativeName = currentEntity.nativeName,
                englishName = currentEntity.englishName,
                callingCodes = currentEntity.callingCodes,
            )

            resultList.add(mappedCountryChooserUiModel)

            val currentGroupIdentifier = currentEntity.englishName.firstOrNull()?.uppercaseChar()

            if (i != sortedCountryEntities.size - 1) {
                val nextCountryEntity = sortedCountryEntities[i + 1]
                val nextGroupIdentifier =
                    nextCountryEntity.englishName.firstOrNull()?.uppercaseChar()

                if (currentGroupIdentifier != nextGroupIdentifier) {
                    resultList.add(
                        CountryChooserUiModel.AlphabetDivider(
                            firstLetterId = nextGroupIdentifier.toString(),
                        ),
                    )
                }
            }
        }

        return resultList
    }

    fun toCountryEntity(countryChooserUiModel: CountryChooserUiModel.Country): CountryEntity =
        CountryEntity(
            flagEmoji = countryChooserUiModel.flagEmoji,
            countryCode = countryChooserUiModel.countryCode,
            nativeName = countryChooserUiModel.nativeName,
            englishName = countryChooserUiModel.englishName,
            callingCodes = countryChooserUiModel.callingCodes,
        )
}
