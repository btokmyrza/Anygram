package kz.btokmyrza.anygram.feature.auth.common.data.mapper

import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthStateEntity
import org.drinkless.tdlib.TdApi

class AuthStateMapper {

    fun toAuthStateEntity(authorizationState: TdApi.AuthorizationState): AuthStateEntity =
        when (authorizationState) {
            is TdApi.AuthorizationStateWaitTdlibParameters -> {
                AuthStateEntity.WaitingForAuthenticationStart
            }
            is TdApi.AuthorizationStateWaitPhoneNumber -> {
                AuthStateEntity.WaitingForPhoneNumber
            }
            is TdApi.AuthorizationStateWaitCode -> {
                AuthStateEntity.WaitingForConfirmationCode
            }
            is TdApi.AuthorizationStateWaitPassword -> {
                AuthStateEntity.WaitingForPassword
            }
            is TdApi.AuthorizationStateReady -> {
                AuthStateEntity.Authenticated
            }
            else -> {
                AuthStateEntity.Unauthenticated
            }
        }
}