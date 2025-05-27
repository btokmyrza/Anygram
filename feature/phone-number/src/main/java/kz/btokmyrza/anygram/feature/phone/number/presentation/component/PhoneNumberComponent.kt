package kz.btokmyrza.anygram.feature.phone.number.presentation.component

import kotlinx.coroutines.flow.StateFlow
import kz.btokmyrza.anygram.feature.country.chooser.domain.model.CountryChooserEntity
import kz.btokmyrza.anygram.feature.country.chooser.presentation.model.CountryChooserUiModel
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberIntent
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberUiState

interface PhoneNumberComponent {

    val uiState: StateFlow<PhoneNumberUiState>

    fun onIntent(intent: PhoneNumberIntent)

    val onNavigateToCountryChooser: (List<CountryChooserEntity>) -> Unit

    val onNavigateToOtp: () -> Unit

    fun onCountryChosen(country: CountryChooserUiModel.Country?)
}
