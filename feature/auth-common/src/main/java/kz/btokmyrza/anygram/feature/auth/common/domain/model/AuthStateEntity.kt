package kz.btokmyrza.anygram.feature.auth.common.domain.model

sealed interface AuthStateEntity {

    data object Unauthenticated : AuthStateEntity

    data object WaitingForAuthenticationStart : AuthStateEntity

    data object WaitingForPhoneNumber : AuthStateEntity

    data class WaitingForOtp(
        val phoneNumber: String,
        val codeType: AuthCodeInfoEntity,
        val nextCodeType: AuthCodeInfoEntity?,
        val codeResendTimeout: Int,
    ) : AuthStateEntity

    data object WaitingForPassword : AuthStateEntity

    data object Authenticated : AuthStateEntity
}
