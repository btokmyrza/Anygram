package kz.btokmyrza.anygram.feature.phone.number.domain.use.cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kz.btokmyrza.anygram.feature.phone.number.domain.model.CountryEntity
import kz.btokmyrza.anygram.feature.phone.number.domain.repository.CountryRepository

class GetCurrentLocationCountryUseCase(
    private val countryRepository: CountryRepository,
) {

    operator fun invoke(): Flow<CountryEntity> =
        countryRepository.getCurrentLocationCountryLetterCode()
            .mapNotNull { letterCode ->
                countryRepository.getCountries().find { countryEntity ->
                    countryEntity.countryLetterCode.equals(other = letterCode, ignoreCase = true)
                }
            }
}
