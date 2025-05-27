package kz.btokmyrza.library.core.presentation.ui.components.topbar

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
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
    onSearchQueryChanged: (String) -> Unit,
) {
    TopAppBar(
        title = {
            SearchableTopBarTitle(
                title = title,
                searchQuery = searchQuery,
                isSearching = isSearching,
                onSearchQueryChanged = onSearchQueryChanged,
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
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

@Composable
private fun SearchableTopBarTitle(
    title: String,
    searchQuery: String,
    isSearching: Boolean,
    onSearchQueryChanged: (String) -> Unit,
) {
    val searchFieldFocusRequester = remember { FocusRequester() }

    LaunchedEffect(isSearching) {
        if (isSearching) {
            searchFieldFocusRequester.requestFocus()
        }
    }

    Crossfade(targetState = isSearching) { isSearching ->
        if (isSearching) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(searchFieldFocusRequester),
                singleLine = true,
                value = searchQuery,
                onValueChange = { newValue ->
                    onSearchQueryChanged(newValue)
                },
                textStyle = AnygramTheme.fontStyles.titleLarge.copy(
                    fontWeight = FontWeight.Normal,
                    color = AnygramTheme.colors.textPrimary,
                ),
                cursorBrush = SolidColor(AnygramTheme.colors.primary),
                decorationBox = { innerTextField ->
                    Box(contentAlignment = Alignment.CenterStart) {
                        if (searchQuery.isBlank()) {
                            Text(
                                text = "Search",
                                style = AnygramTheme.fontStyles.bodyLarge,
                                color = AnygramTheme.colors.textSecondary,
                            )
                        }
                        innerTextField()
                    }
                },
            )
        } else {
            Text(
                text = title,
                style = AnygramTheme.fontStyles.titleLarge,
            )
        }
    }
}

@Preview
@Composable
private fun SearchableTopBarPreview() {
    var isSearching by remember { mutableStateOf(false) }

    AnygramTheme {
        SearchableTopBar(
            title = "Title",
            searchQuery = "Kaz",
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
