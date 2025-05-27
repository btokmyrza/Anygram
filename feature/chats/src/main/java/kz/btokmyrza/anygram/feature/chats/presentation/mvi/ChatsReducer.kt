package kz.btokmyrza.anygram.feature.chats.presentation.mvi

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kz.btokmyrza.anygram.feature.chats.domain.model.ChatEntity

object ChatsReducer {

    fun getInitialState(): ChatsUiState =
        ChatsUiState(
            isLoading = false,
            chats = persistentListOf(),
        )

    fun ChatsUiState.loading(): ChatsUiState =
        copy(isLoading = true)

    fun ChatsUiState.loaded(chats: List<ChatEntity>): ChatsUiState =
        copy(
            isLoading = false,
            chats = chats.toPersistentList(),
        )
}
