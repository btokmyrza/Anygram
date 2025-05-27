package kz.btokmyrza.anygram.feature.phone.number.domain.use.cases

import kz.btokmyrza.anygram.feature.phone.number.domain.model.CountryEntity
import kz.btokmyrza.anygram.feature.phone.number.domain.repository.CountryRepository

class GetCountriesUseCase(
    private val countryRepository: CountryRepository,
) {

    suspend operator fun invoke(): List<CountryEntity> =
        countryRepository.getCountries()
}
