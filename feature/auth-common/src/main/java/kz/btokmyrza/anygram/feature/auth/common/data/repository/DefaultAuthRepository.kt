package kz.btokmyrza.anygram.feature.auth.common.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kz.btokmyrza.anygram.feature.auth.common.data.mapper.AuthActionMapper
import kz.btokmyrza.anygram.feature.auth.common.data.mapper.AuthActionResultMapper
import kz.btokmyrza.anygram.feature.auth.common.data.mapper.AuthStateMapper
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthActionEntity
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthActionResultEntity
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthStateEntity
import kz.btokmyrza.anygram.feature.auth.common.domain.repository.AuthRepository
import kz.btokmyrza.library.core.data.datasource.TdlibDataSource
import org.drinkless.tdlib.TdApi

class DefaultAuthRepository(
    private val tdlibDataSource: TdlibDataSource,
    private val authStateMapper: AuthStateMapper,
    private val authActionMapper: AuthActionMapper,
    private val authActionResultMapper: AuthActionResultMapper,
) : AuthRepository {

    override fun sendAction(authActionEntity: AuthActionEntity): Flow<AuthActionResultEntity> =
        tdlibDataSource.sendAsync(authActionMapper.toTdApiFunction(authActionEntity))
            .map { update ->
                authActionResultMapper.toAuthActionResult(update)
            }

    override fun observeUpdates(): Flow<AuthStateEntity> =
        tdlibDataSource.updates
            .filterIsInstance<TdApi.UpdateAuthorizationState>()
            .map { update ->
                authStateMapper.toAuthStateEntity(update.authorizationState)
            }
}
