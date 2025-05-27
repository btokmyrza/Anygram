package kz.btokmyrza.anygram.feature.auth.common.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthActionEntity
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthActionResultEntity
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthStateEntity

interface AuthRepository {

    fun sendAction(authActionEntity: AuthActionEntity): Flow<AuthActionResultEntity>

    fun observeUpdates(): Flow<AuthStateEntity>
}
