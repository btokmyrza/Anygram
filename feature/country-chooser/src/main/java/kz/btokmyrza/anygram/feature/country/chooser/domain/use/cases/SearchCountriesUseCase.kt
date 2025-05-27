package kz.btokmyrza.anygram.feature.country.chooser.domain.use.cases

import kz.btokmyrza.anygram.feature.country.chooser.domain.model.CountryChooserEntity

class SearchCountriesUseCase {

    operator fun invoke(
        query: String,
        countries: List<CountryChooserEntity>,
    ): List<CountryChooserEntity> =
        countries.filter { country ->
            country.englishName.split(spaceCharRegex).any { word ->
                word.startsWith(prefix = query, ignoreCase = true)
            }
        }

    private companion object {

        val spaceCharRegex = "\\s".toRegex()
    }
}
