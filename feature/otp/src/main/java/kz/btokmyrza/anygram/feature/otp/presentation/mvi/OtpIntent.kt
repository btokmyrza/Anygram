package kz.btokmyrza.anygram.feature.otp.presentation.mvi

sealed interface OtpIntent {

    data class OnFieldFocused(
        val index: Int,
    ): OtpIntent

    data class OnCodeChanged(
        val code: String,
    ) : OtpIntent

    data class OnCodeDeleted(
        val index: Int,
    ) : OtpIntent

    data object OnBackClicked : OtpIntent
}
