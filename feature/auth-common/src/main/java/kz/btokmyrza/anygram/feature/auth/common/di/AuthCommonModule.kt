package kz.btokmyrza.anygram.feature.auth.common.di

import kz.btokmyrza.anygram.feature.auth.common.data.mapper.AuthActionMapper
import kz.btokmyrza.anygram.feature.auth.common.data.mapper.AuthActionResultMapper
import kz.btokmyrza.anygram.feature.auth.common.data.mapper.AuthStateMapper
import kz.btokmyrza.anygram.feature.auth.common.data.repository.DefaultAuthRepository
import kz.btokmyrza.anygram.feature.auth.common.domain.repository.AuthRepository
import kz.btokmyrza.anygram.feature.auth.common.domain.use.cases.ObserveAuthUpdatesUseCase
import org.koin.dsl.module

val authCommonModule = module {

    factory { AuthStateMapper() }
    factory { AuthActionMapper() }
    factory { AuthActionResultMapper() }

    single<AuthRepository> {
        DefaultAuthRepository(
            tdlibDataSource = get(),
            authStateMapper = get(),
            authActionMapper = get(),
            authActionResultMapper = get(),
        )
    }

    factory {
        ObserveAuthUpdatesUseCase(
            authRepository = get(),
        )
    }
}
