package kz.btokmyrza.anygram.feature.auth.common.data.mapper

import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthActionEntity
import org.drinkless.tdlib.TdApi

class AuthActionMapper {

    fun toTdApiFunction(authActionEntity: AuthActionEntity): TdApi.Function<out TdApi.Object> =
        when (authActionEntity) {
            is AuthActionEntity.StartAuth -> {
                TdApi.SetTdlibParameters().apply {
                    useTestDc = authActionEntity.useTestDc
                    databaseDirectory = authActionEntity.databaseDirectory
                    filesDirectory = authActionEntity.filesDirectory
                    databaseEncryptionKey = authActionEntity.databaseEncryptionKey.toByteArray()
                    useFileDatabase = authActionEntity.useFileDatabase
                    useChatInfoDatabase = authActionEntity.useChatInfoDatabase
                    useMessageDatabase = authActionEntity.useMessageDatabase
                    useSecretChats = authActionEntity.useSecretChats
                    apiId = authActionEntity.apiId
                    apiHash = authActionEntity.apiHash
                    systemLanguageCode = authActionEntity.systemLanguageCode
                    deviceModel = authActionEntity.deviceModel
                    systemVersion = authActionEntity.systemVersion
                    applicationVersion = authActionEntity.applicationVersion
                }
            }
            is AuthActionEntity.SendPhoneNumber -> {
                TdApi.SetAuthenticationPhoneNumber().apply {
                    phoneNumber = authActionEntity.phoneNumber
                    settings = null
                }
            }
            is AuthActionEntity.SendOtp -> {
                TdApi.CheckAuthenticationCode().apply {
                    code = authActionEntity.code
                }
            }
        }
}
