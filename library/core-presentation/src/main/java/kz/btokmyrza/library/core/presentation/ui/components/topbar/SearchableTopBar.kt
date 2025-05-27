package kz.btokmyrza.library.core.presentation.ui.components.topbar

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableTopBar(
    title: String,
    searchQuery: String,
    isSearching: Boolean,
    onBackClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onSearchQueryChanged: (String) -> Unit
) {
    TopAppBar(
        title = {
            Crossfade(targetState = isSearching) { isSearching ->
                if (isSearching) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = searchQuery,
                        onValueChange = { textFieldValue ->
                            onSearchQueryChanged(textFieldValue)
                        },
                        placeholder = { Text(text = "Search") },
                        singleLine = true,
                    )
                } else {
                    Text(text = title)
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    if (isSearching) {
                        onSearchQueryChanged("")
                    } else {
                        onBackClicked()
                    }
                },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
        actions = {
            if (!isSearching) {
                IconButton(onClick = onSearchClicked) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun SearchableTopBarPreview() {
    var isSearching by remember { mutableStateOf(false) }

    AnygramTheme {
        SearchableTopBar(
            title = "Title",
            searchQuery = "Kazakhstan",
            isSearching = isSearching,
            onBackClicked = {
                if (isSearching) {
                    isSearching = false
                }
            },
            onSearchClicked = { isSearching = true },
            onSearchQueryChanged = {},
        )
    }
}
