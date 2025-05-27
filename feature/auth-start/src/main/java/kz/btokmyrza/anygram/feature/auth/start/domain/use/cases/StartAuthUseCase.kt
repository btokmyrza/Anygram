package kz.btokmyrza.anygram.feature.auth.start.domain.use.cases

import kotlinx.coroutines.flow.Flow
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthActionEntity
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthActionResultEntity
import kz.btokmyrza.anygram.feature.auth.common.domain.repository.AuthRepository
import kz.btokmyrza.anygram.feature.auth.start.domain.model.AuthParameters

class StartAuthUseCase(
    private val authRepository: AuthRepository,
    private val authParameters: AuthParameters,
) {

    operator fun invoke(): Flow<AuthActionResultEntity> {
        val authActionEntity = AuthActionEntity.StartAuth(
            useTestDc = authParameters.useTestDc,
            databaseDirectory = authParameters.databaseDirectory,
            filesDirectory = authParameters.filesDirectory,
            databaseEncryptionKey = authParameters.databaseEncryptionKey,
            useFileDatabase = authParameters.useFileDatabase,
            useChatInfoDatabase = authParameters.useChatInfoDatabase,
            useMessageDatabase = authParameters.useMessageDatabase,
            useSecretChats = authParameters.useSecretChats,
            apiId = authParameters.apiId,
            apiHash = authParameters.apiHash,
            systemLanguageCode = authParameters.systemLanguageCode,
            deviceModel = authParameters.deviceModel,
            systemVersion = authParameters.systemVersion,
            applicationVersion = authParameters.applicationVersion,
        )

        return authRepository.sendAction(authActionEntity)
    }
}