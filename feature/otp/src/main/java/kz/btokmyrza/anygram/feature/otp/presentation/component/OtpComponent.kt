package kz.btokmyrza.anygram.feature.otp.presentation.component

import kotlinx.coroutines.flow.StateFlow
import kz.btokmyrza.anygram.feature.otp.presentation.mvi.OtpIntent
import kz.btokmyrza.anygram.feature.otp.presentation.mvi.OtpUiState

interface OtpComponent {

    val uiState: StateFlow<OtpUiState>

    fun onIntent(intent: OtpIntent)

    val onNavigateToHome: () -> Unit

    val onNavigateBack: () -> Unit
}
