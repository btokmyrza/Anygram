package kz.btokmyrza.anygram.feature.country.chooser.presentation.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import kz.btokmyrza.library.core.presentation.ui.components.topbar.SearchableTopBar
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

@Composable
internal fun CountryChooserTopBar(
    searchQuery: String,
    isSearching: Boolean,
    onBackClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
) {
    SearchableTopBar(
        title = "Choose a country",
        searchQuery = searchQuery,
        isSearching = isSearching,
        onBackClicked = onBackClicked,
        onSearchClicked = onSearchClicked,
        onSearchQueryChanged = onSearchQueryChanged,
    )
}

@Preview
@Composable
private fun CountryChooserTopBarPreview() {
    var isSearching by remember { mutableStateOf(false) }

    AnygramTheme {
        CountryChooserTopBar(
            searchQuery = "Kazakhstan",
            isSearching = isSearching,
            onBackClicked = {
                if (isSearching) {
                    isSearching = false
                }
            },
            onSearchClicked = { isSearching = !isSearching },
            onSearchQueryChanged = {},
        )
    }
}
