package kz.btokmyrza.anygram.feature.chats.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import kz.btokmyrza.anygram.feature.chats.presentation.component.ChatsComponent
import kz.btokmyrza.anygram.feature.chats.presentation.mvi.ChatsUiState
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

@Composable
fun ChatsScreen(chatsComponent: ChatsComponent) {
    val uiState by chatsComponent.uiState.collectAsStateWithLifecycle()

    ChatsScreenContent(
        uiState = uiState,
    )
}

@Composable
private fun ChatsScreenContent(
    uiState: ChatsUiState,
) {
    LazyColumn {
        items(
            items = uiState.chats,
            key = { chatEntity -> chatEntity.id },
            contentType = { "chatItem" },
        ) { chatEntity ->

        }
    }
}

@Preview
@Composable
private fun ChatsScreenPreview() {
    val stubUiState = ChatsUiState(
        isLoading = false,
        chats = persistentListOf(),
    )

    AnygramTheme {
        ChatsScreenContent(
            uiState = stubUiState,
        )
    }
}
