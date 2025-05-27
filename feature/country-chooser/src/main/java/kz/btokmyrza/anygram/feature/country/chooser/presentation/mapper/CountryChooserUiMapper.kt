package kz.btokmyrza.anygram.feature.country.chooser.presentation.mapper

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kz.btokmyrza.anygram.feature.country.chooser.domain.model.CountryChooserEntity
import kz.btokmyrza.anygram.feature.country.chooser.presentation.model.CountryChooserUiModel

class CountryChooserUiMapper {

    fun toCountryUiModelList(
        countryEntities: List<CountryChooserEntity>,
    ): ImmutableList<CountryChooserUiModel> {
        val resultList = mutableListOf<CountryChooserUiModel>()

        for (i in countryEntities.indices) {
            val currentEntity = countryEntities[i]
            val mappedCountryChooserUiModel = CountryChooserUiModel.Country(
                flagEmoji = currentEntity.flagEmoji,
                countryLetterCode = currentEntity.countryLetterCode,
                nativeName = currentEntity.nativeName,
                englishName = currentEntity.englishName,
                callingCodes = currentEntity.callingCodes,
            )

            resultList.add(mappedCountryChooserUiModel)

            val currentGroupIdentifier = currentEntity.englishName.firstOrNull()?.uppercaseChar()

            if (i != countryEntities.size - 1) {
                val nextCountryEntity = countryEntities[i + 1]
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

        return resultList.toImmutableList()
    }
}
