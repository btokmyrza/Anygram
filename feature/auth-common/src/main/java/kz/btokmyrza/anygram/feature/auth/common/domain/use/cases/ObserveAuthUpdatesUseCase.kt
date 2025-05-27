package kz.btokmyrza.anygram.feature.auth.common.domain.use.cases

import kotlinx.coroutines.flow.Flow
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthStateEntity
import kz.btokmyrza.anygram.feature.auth.common.domain.repository.AuthRepository

class ObserveAuthUpdatesUseCase(
    private val authRepository: AuthRepository,
) {

    operator fun invoke(): Flow<AuthStateEntity> =
        authRepository.observeUpdates()
}
