package kz.btokmyrza.anygram.feature.phone.number.data.mapper

import kz.btokmyrza.anygram.feature.phone.number.domain.model.CountryEntity
import org.drinkless.tdlib.TdApi

class CountryMapper {

    fun toCountryEntity(
        flagEmoji: String,
        countryInfo: TdApi.CountryInfo,
    ): CountryEntity =
        CountryEntity(
            flagEmoji = flagEmoji,
            countryLetterCode = countryInfo.countryCode,
            nativeName = countryInfo.name,
            englishName = countryInfo.englishName,
            callingCodes = countryInfo.callingCodes.toList(),
        )
}
