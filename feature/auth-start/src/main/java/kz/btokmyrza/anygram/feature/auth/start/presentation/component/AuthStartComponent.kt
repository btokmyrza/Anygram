package kz.btokmyrza.anygram.feature.auth.start.presentation.component

import kotlinx.coroutines.flow.StateFlow
import kz.btokmyrza.anygram.feature.auth.start.presentation.mvi.AuthStartIntent
import kz.btokmyrza.anygram.feature.auth.start.presentation.mvi.AuthStartUiState

interface AuthStartComponent {

    val uiState: StateFlow<AuthStartUiState>

    fun onIntent(intent: AuthStartIntent)

    val onNavigateToPhoneNumber: () -> Unit
}
