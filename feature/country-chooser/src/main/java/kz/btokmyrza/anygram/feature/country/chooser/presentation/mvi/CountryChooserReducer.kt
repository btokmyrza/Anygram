package kz.btokmyrza.anygram.feature.country.chooser.presentation.mvi

import kz.btokmyrza.anygram.feature.country.chooser.presentation.model.CountryChooserUiModel

object CountryChooserReducer {

    fun getInitialState(countries: List<CountryChooserUiModel>): CountryChooserUiState =
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
        countries: List<CountryChooserUiModel>,
    ): CountryChooserUiState =
        copy(displayedCountries = countries)
}
