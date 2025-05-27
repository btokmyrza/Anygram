package kz.btokmyrza.anygram.feature.country.chooser.presentation.mvi

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kz.btokmyrza.anygram.feature.country.chooser.presentation.model.CountryChooserUiModel

@Stable
data class CountryChooserUiState(
    val searchQuery: String,
    val isSearching: Boolean,
    val displayedCountries: ImmutableList<CountryChooserUiModel>,
)
