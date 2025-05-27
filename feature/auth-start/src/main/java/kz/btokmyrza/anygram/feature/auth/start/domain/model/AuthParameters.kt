package kz.btokmyrza.anygram.feature.auth.start.domain.model

class AuthParameters(
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
)