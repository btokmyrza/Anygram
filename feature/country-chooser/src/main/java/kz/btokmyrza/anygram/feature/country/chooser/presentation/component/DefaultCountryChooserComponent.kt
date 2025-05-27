package kz.btokmyrza.anygram.feature.country.chooser.presentation.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kz.btokmyrza.anygram.feature.country.chooser.domain.model.CountryChooserEntity
import kz.btokmyrza.anygram.feature.country.chooser.domain.use.cases.SearchCountriesUseCase
import kz.btokmyrza.anygram.feature.country.chooser.presentation.mapper.CountryChooserUiMapper
import kz.btokmyrza.anygram.feature.country.chooser.presentation.model.CountryChooserUiModel
import kz.btokmyrza.anygram.feature.country.chooser.presentation.mvi.CountryChooserIntent
import kz.btokmyrza.anygram.feature.country.chooser.presentation.mvi.CountryChooserReducer
import kz.btokmyrza.anygram.feature.country.chooser.presentation.mvi.CountryChooserReducer.displayedCountriesChanged
import kz.btokmyrza.anygram.feature.country.chooser.presentation.mvi.CountryChooserReducer.searchQueryChanged
import kz.btokmyrza.anygram.feature.country.chooser.presentation.mvi.CountryChooserReducer.searchStopped
import kz.btokmyrza.anygram.feature.country.chooser.presentation.mvi.CountryChooserReducer.searching
import kz.btokmyrza.anygram.feature.country.chooser.presentation.mvi.CountryChooserUiState
import kz.btokmyrza.library.core.presentation.base.navigation.BaseComponent
import kz.btokmyrza.library.core.presentation.extensions.updateUiState

class DefaultCountryChooserComponent(
    componentContext: ComponentContext,
    override val onNavigateBack: (CountryChooserUiModel.Country?) -> Unit,
    private val countryChooserUiMapper: CountryChooserUiMapper,
    private val searchCountriesUseCase: SearchCountriesUseCase,
    private val countries: List<CountryChooserEntity>,
) : BaseComponent(componentContext), CountryChooserComponent {

    private val allCountries = countryChooserUiMapper.toCountryUiModelList(countries)

    private val _uiState = MutableStateFlow<CountryChooserUiState>(
        value = CountryChooserReducer.getInitialState(allCountries),
    )
    override val uiState: StateFlow<CountryChooserUiState> = _uiState.asStateFlow()

    override fun onIntent(intent: CountryChooserIntent) {
        when (intent) {
            CountryChooserIntent.OnBackClicked -> {
                onBackClicked()
            }
            CountryChooserIntent.OnSearchClicked -> {
                onSearchClicked()
            }
            is CountryChooserIntent.OnSearchQueryChanged -> {
                onSearchQueryChanged(intent.query)
            }
            is CountryChooserIntent.OnCountryClicked -> {
                onCountryClicked(intent.countryUiModel)
            }
        }
    }

    private fun onBackClicked() {
        val uiState = _uiState.value

        if (uiState.isSearching) {
            _uiState.updateUiState { searchStopped() }
        } else {
            onNavigateBack(null)
        }
    }

    private fun onSearchClicked() {
        _uiState.updateUiState { searching() }
    }

    private fun onSearchQueryChanged(query: String) {
        _uiState.updateUiState { searchQueryChanged(query) }

        val foundCountries = if (query.isBlank()) {
            allCountries
        } else {
            searchCountriesUseCase(query = query, countries = countries)
                .let(countryChooserUiMapper::toCountryUiModelList)
        }

        _uiState.updateUiState { displayedCountriesChanged(foundCountries) }
    }

    private fun onCountryClicked(countryUiModel: CountryChooserUiModel.Country) {
        onNavigateBack(countryUiModel)
    }
}
