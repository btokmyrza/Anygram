package kz.btokmyrza.anygram.feature.auth.common.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthActionEntity
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthActionResult
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthStateEntity

interface AuthRepository {

    fun sendAction(authActionEntity: AuthActionEntity): Flow<AuthActionResult>

    fun observeUpdates(): Flow<AuthStateEntity>
}
