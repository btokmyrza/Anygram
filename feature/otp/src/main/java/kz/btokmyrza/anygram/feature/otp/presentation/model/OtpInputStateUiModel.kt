package kz.btokmyrza.anygram.feature.otp.presentation.model

sealed interface OtpInputStateUiModel {

    data object Default : OtpInputStateUiModel

    data object Error : OtpInputStateUiModel

    data object Success : OtpInputStateUiModel
}
