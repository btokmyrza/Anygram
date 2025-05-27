package kz.btokmyrza.anygram.feature.auth.common.domain.model

sealed interface AuthActionResult {

    data object Success : AuthActionResult

    data class Error(val message: String) : AuthActionResult
}
