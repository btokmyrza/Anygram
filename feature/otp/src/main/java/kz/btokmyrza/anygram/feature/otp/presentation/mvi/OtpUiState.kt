package kz.btokmyrza.anygram.feature.otp.presentation.mvi

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList

@Stable
data class OtpUiState(
    val totalCode: ImmutableList<String?>,
    val codeLength: Int,
    val focusedIndex: Int,
)
