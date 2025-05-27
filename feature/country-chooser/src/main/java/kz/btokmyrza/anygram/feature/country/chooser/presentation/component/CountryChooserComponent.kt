package kz.btokmyrza.anygram.feature.country.chooser.presentation.component

import kotlinx.coroutines.flow.StateFlow
import kz.btokmyrza.anygram.feature.country.chooser.presentation.model.CountryChooserUiModel
import kz.btokmyrza.anygram.feature.country.chooser.presentation.mvi.CountryChooserIntent
import kz.btokmyrza.anygram.feature.country.chooser.presentation.mvi.CountryChooserUiState

interface CountryChooserComponent {

    val uiState: StateFlow<CountryChooserUiState>

    fun onIntent(intent: CountryChooserIntent)

    val onNavigateBack: (CountryChooserUiModel.Country?) -> Unit
}
