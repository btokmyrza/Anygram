package kz.btokmyrza.anygram.feature.chats.presentation.component

import kotlinx.coroutines.flow.StateFlow
import kz.btokmyrza.anygram.feature.chats.presentation.mvi.ChatsUiState

interface ChatsComponent {

    val uiState: StateFlow<ChatsUiState>
}
