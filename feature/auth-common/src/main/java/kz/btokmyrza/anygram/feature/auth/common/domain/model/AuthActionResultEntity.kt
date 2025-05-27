package kz.btokmyrza.anygram.feature.auth.common.domain.model

sealed interface AuthActionResultEntity {

    data object Success : AuthActionResultEntity

    data class Error(val message: String) : AuthActionResultEntity
}
