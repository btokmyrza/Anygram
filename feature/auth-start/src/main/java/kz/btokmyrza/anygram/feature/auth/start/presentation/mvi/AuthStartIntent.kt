package kz.btokmyrza.anygram.feature.auth.start.presentation.mvi

sealed interface AuthStartIntent {

    data object OnDarkModeToggled : AuthStartIntent

    data object OnStartMessagingClicked : AuthStartIntent
}