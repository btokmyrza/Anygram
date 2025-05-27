package kz.btokmyrza.anygram.feature.chats.presentation.mvi

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kz.btokmyrza.anygram.feature.chats.domain.model.ChatEntity

@Stable
data class ChatsUiState(
    val isLoading: Boolean,
    val chats: ImmutableList<ChatEntity>,
)
