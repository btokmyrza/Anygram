package kz.btokmyrza.anygram.feature.country.chooser.presentation.mvi

import kz.btokmyrza.anygram.feature.country.chooser.presentation.model.CountryChooserUiModel

data class CountryChooserUiState(
    val searchQuery: String,
    val isSearching: Boolean,
    val displayedCountries: List<CountryChooserUiModel>,
)
