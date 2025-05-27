package kz.btokmyrza.anygram.feature.country.chooser.presentation.mvi

import kotlinx.collections.immutable.ImmutableList
import kz.btokmyrza.anygram.feature.country.chooser.presentation.model.CountryChooserUiModel

object CountryChooserReducer {

    fun getInitialState(countries: ImmutableList<CountryChooserUiModel>): CountryChooserUiState =
        CountryChooserUiState(
            searchQuery = "",
            isSearching = false,
            displayedCountries = countries,
        )


    fun CountryChooserUiState.searching(): CountryChooserUiState =
        copy(isSearching = true)

    fun CountryChooserUiState.searchStopped(): CountryChooserUiState =
        copy(isSearching = false)

    fun CountryChooserUiState.searchQueryChanged(searchQuery: String): CountryChooserUiState =
        copy(searchQuery = searchQuery)

    fun CountryChooserUiState.displayedCountriesChanged(
        countries: ImmutableList<CountryChooserUiModel>,
    ): CountryChooserUiState =
        copy(displayedCountries = countries)
}
