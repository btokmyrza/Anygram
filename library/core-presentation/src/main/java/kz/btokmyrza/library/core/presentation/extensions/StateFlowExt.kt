package kz.btokmyrza.library.core.presentation.extensions

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun <S> MutableStateFlow<S>.updateUiState(builder: S.() -> S) {
    this.update { currentState ->
        currentState.builder()
    }
}
