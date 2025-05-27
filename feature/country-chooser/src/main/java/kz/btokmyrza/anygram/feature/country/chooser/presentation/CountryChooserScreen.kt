package kz.btokmyrza.anygram.feature.country.chooser.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kz.btokmyrza.anygram.feature.country.chooser.presentation.component.CountryChooserComponent
import kz.btokmyrza.anygram.feature.country.chooser.presentation.model.CountryChooserUiModel
import kz.btokmyrza.anygram.feature.country.chooser.presentation.mvi.CountryChooserIntent
import kz.btokmyrza.anygram.feature.country.chooser.presentation.widgets.CountryChooserTopBar
import kz.btokmyrza.anygram.feature.country.chooser.presentation.widgets.CountryCodeItem
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

@Composable
fun CountryChooserScreen(component: CountryChooserComponent) {
    BackHandler { component.onIntent(CountryChooserIntent.OnBackClicked) }

    val uiState by component.uiState.collectAsStateWithLifecycle()

    CountryChooserContent(
        searchQuery = uiState.searchQuery,
        isSearching = uiState.isSearching,
        displayedCountries = uiState.displayedCountries,
        onIntent = component::onIntent,
    )
}

@Composable
private fun CountryChooserContent(
    searchQuery: String,
    isSearching: Boolean,
    displayedCountries: ImmutableList<CountryChooserUiModel>,
    onIntent: (CountryChooserIntent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        topBar = {
            CountryChooserTopBar(
                searchQuery = searchQuery,
                isSearching = isSearching,
                onBackClicked = {
                    onIntent(CountryChooserIntent.OnBackClicked)
                },
                onSearchClicked = {
                    onIntent(CountryChooserIntent.OnSearchClicked)
                },
                onSearchQueryChanged = { newSearchQuery ->
                    onIntent(CountryChooserIntent.OnSearchQueryChanged(newSearchQuery))
                },
            )
        },
    ) { paddingValues ->
        CountryChooserMainContent(
            modifier = Modifier.padding(paddingValues),
            displayedCountries = displayedCountries,
            onCountryClicked = { country ->
                onIntent(CountryChooserIntent.OnCountryClicked(country))
            },
        )
    }
}

@Composable
private fun CountryChooserMainContent(
    modifier: Modifier = Modifier,
    displayedCountries: ImmutableList<CountryChooserUiModel>,
    onCountryClicked: (CountryChooserUiModel.Country) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp,
        ),
    ) {
        items(
            items = displayedCountries,
            key = { countryChooserUiModel ->
                when (countryChooserUiModel) {
                    is CountryChooserUiModel.Country -> {
                        countryChooserUiModel.countryLetterCode
                    }
                    is CountryChooserUiModel.AlphabetDivider -> {
                        countryChooserUiModel.firstLetterId
                    }
                }
            },
            contentType = { countryChooserUiModel ->
                when (countryChooserUiModel) {
                    is CountryChooserUiModel.Country -> {
                        "country"
                    }
                    is CountryChooserUiModel.AlphabetDivider -> {
                        "divider"
                    }
                }
            },
        ) { country ->
            when (country) {
                is CountryChooserUiModel.Country -> {
                    CountryCodeItem(
                        modifier = Modifier.clickable {
                            onCountryClicked(country)
                        },
                        country = country,
                    )
                }
                is CountryChooserUiModel.AlphabetDivider -> {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview
@Composable
private fun CountryChooserPreview() {
    val displayedCountries = persistentListOf(
        CountryChooserUiModel.Country(
            flagEmoji = "🇨🇿",
            countryLetterCode = "+420",
            nativeName = "Česká republika",
            englishName = "Czech Republic",
            callingCodes = listOf("420"),
        ),
        CountryChooserUiModel.AlphabetDivider(firstLetterId = "C"),
        CountryChooserUiModel.Country(
            flagEmoji = "🇩🇪",
            countryLetterCode = "+49",
            nativeName = "Deutschland",
            englishName = "Germany",
            callingCodes = listOf("49"),
        ),
        CountryChooserUiModel.AlphabetDivider(firstLetterId = "G"),
        CountryChooserUiModel.Country(
            flagEmoji = "🇺🇸",
            countryLetterCode = "+1",
            nativeName = "United States",
            englishName = "United States",
            callingCodes = listOf("1"),
        ),
    )

    AnygramTheme {
        CountryChooserContent(
            searchQuery = "",
            isSearching = false,
            displayedCountries = displayedCountries,
            onIntent = {},
        )
    }
}
