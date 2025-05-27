package kz.btokmyrza.anygram.feature.phone.number.domain.use.cases

import kz.btokmyrza.anygram.feature.phone.number.domain.model.CountryEntity
import kz.btokmyrza.anygram.feature.phone.number.domain.repository.CountryRepository

class FindCountryByPhoneCodeUseCase(
    private val countryRepository: CountryRepository,
) {

    suspend operator fun invoke(phoneCode: String): CountryEntity? =
        countryRepository.getCountries().firstOrNull { countryEntity ->
            countryEntity.callingCodes.contains(phoneCode)
        }
}
