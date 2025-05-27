package kz.btokmyrza.anygram.feature.auth.common.domain.model

sealed interface AuthActionEntity {

    data class StartAuth(
        val useTestDc: Boolean,
        val databaseDirectory: String,
        val filesDirectory: String,
        val databaseEncryptionKey: String,
        val useFileDatabase: Boolean,
        val useChatInfoDatabase: Boolean,
        val useMessageDatabase: Boolean,
        val useSecretChats: Boolean,
        val apiId: Int,
        val apiHash: String,
        val systemLanguageCode: String,
        val deviceModel: String,
        val systemVersion: String,
        val applicationVersion: String,
    ) : AuthActionEntity

    data class SendPhoneNumber(
        val phoneNumber: String,
    ) : AuthActionEntity

    data class SendOtp(
        val code: String,
    ) : AuthActionEntity
}
