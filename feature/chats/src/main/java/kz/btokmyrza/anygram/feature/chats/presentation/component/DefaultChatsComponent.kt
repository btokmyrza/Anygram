package kz.btokmyrza.anygram.feature.chats.presentation.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kz.btokmyrza.anygram.feature.chats.domain.use.cases.GetChatsUseCase
import kz.btokmyrza.anygram.feature.chats.presentation.mvi.ChatsReducer
import kz.btokmyrza.anygram.feature.chats.presentation.mvi.ChatsReducer.loaded
import kz.btokmyrza.anygram.feature.chats.presentation.mvi.ChatsReducer.loading
import kz.btokmyrza.anygram.feature.chats.presentation.mvi.ChatsUiState
import kz.btokmyrza.library.core.presentation.base.navigation.BaseComponent
import kz.btokmyrza.library.core.presentation.extensions.updateUiState

class DefaultChatsComponent(
    componentContext: ComponentContext,
    getChatsUseCase: GetChatsUseCase,
) : BaseComponent(componentContext), ChatsComponent {

    private val _uiState = MutableStateFlow(ChatsReducer.getInitialState())
    override val uiState: StateFlow<ChatsUiState> = _uiState

    init {
        getChatsUseCase()
            .onStart {
                _uiState.updateUiState { loading() }
            }
            .onEach { chats ->
                _uiState.updateUiState { loaded(chats) }
            }
            .launchIn(this)
    }
}
