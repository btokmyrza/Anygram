package kz.btokmyrza.anygram.feature.auth.start.presentation.mvi

object AuthStartReducer {

    fun getInitialState(): AuthStartUiState =
        AuthStartUiState(
            isDarkModeEnabled = false,
            isStartMessagingEnabled = false,
        )

    fun loaded(
        isDarkModeEnabled: Boolean,
        isStartMessagingEnabled: Boolean,
    ): AuthStartUiState =
        AuthStartUiState(
            isDarkModeEnabled = isDarkModeEnabled,
            isStartMessagingEnabled = isStartMessagingEnabled,
        )
}
