package kz.btokmyrza.anygram.feature.otp.presentation.mvi

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kz.btokmyrza.anygram.feature.otp.presentation.model.OtpInputStateUiModel

@Stable
data class OtpUiState(
    val phoneNumber: String,
    val codeType: String,
    val nextCodeType: String?,
    val codeResendTimeout: Int,
    val totalCode: ImmutableList<String?>,
    val codeLength: Int,
    val focusedIndex: Int,
    val inputState: OtpInputStateUiModel,
)
