package kz.btokmyrza.anygram.feature.auth.common.data.mapper

import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthCodeInfoEntity
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
                AuthStateEntity.WaitingForOtp(
                    phoneNumber = authorizationState.codeInfo.phoneNumber,
                    codeType = authorizationState.codeInfo.type.let(::toAuthCodeInfoEntity),
                    nextCodeType = authorizationState.codeInfo.nextType?.let(::toAuthCodeInfoEntity),
                    codeResendTimeout = authorizationState.codeInfo.timeout,
                )
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

    private fun toAuthCodeInfoEntity(codeType: TdApi.AuthenticationCodeType): AuthCodeInfoEntity =
        when (codeType) {
            is TdApi.AuthenticationCodeTypeTelegramMessage -> {
                AuthCodeInfoEntity.TelegramMessage(
                    length = codeType.length,
                )
            }
            is TdApi.AuthenticationCodeTypeSms -> {
                AuthCodeInfoEntity.SmsCode(
                    length = codeType.length,
                )
            }
            is TdApi.AuthenticationCodeTypeSmsWord -> {
                AuthCodeInfoEntity.SmsWord(
                    firstLetter = codeType.firstLetter,
                )
            }
            is TdApi.AuthenticationCodeTypeSmsPhrase -> {
                AuthCodeInfoEntity.SmsPhrase(
                    firstWord = codeType.firstWord,
                )
            }
            is TdApi.AuthenticationCodeTypeCall -> {
                AuthCodeInfoEntity.Call(
                    length = codeType.length,
                )
            }
            is TdApi.AuthenticationCodeTypeFlashCall -> {
                AuthCodeInfoEntity.FlashCall(
                    pattern = codeType.pattern,
                )
            }
            is TdApi.AuthenticationCodeTypeMissedCall -> {
                AuthCodeInfoEntity.MissedCall(
                    phoneNumberPrefix = codeType.phoneNumberPrefix,
                    length = codeType.length,
                )
            }
            is TdApi.AuthenticationCodeTypeFragment -> {
                AuthCodeInfoEntity.Fragment(
                    url = codeType.url,
                    length = codeType.length,
                )
            }
            is TdApi.AuthenticationCodeTypeFirebaseAndroid -> {
                AuthCodeInfoEntity.TelegramMessage(
                    length = 5,
                )
            }
            else -> {
                AuthCodeInfoEntity.TelegramMessage(
                    length = 5,
                )
            }
        }
}
