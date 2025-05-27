package kz.btokmyrza.anygram.feature.otp.presentation.mvi

sealed interface OtpIntent {

    data class OnFieldFocused(
        val index: Int,
    ) : OtpIntent

    data class OnCodeChanged(
        val code: String,
    ) : OtpIntent

    data object OnCodeDeleted : OtpIntent

    data object OnBackClicked : OtpIntent
}
