package kz.btokmyrza.anygram.feature.country.chooser.presentation.mvi

import kz.btokmyrza.anygram.feature.country.chooser.presentation.model.CountryChooserUiModel

sealed interface CountryChooserIntent {

    data object OnBackClicked : CountryChooserIntent

    data object OnSearchClicked : CountryChooserIntent

    data class OnSearchQueryChanged(
        val query: String,
    ) : CountryChooserIntent

    data class OnCountryClicked(
        val countryUiModel: CountryChooserUiModel.Country,
    ) : CountryChooserIntent
}
