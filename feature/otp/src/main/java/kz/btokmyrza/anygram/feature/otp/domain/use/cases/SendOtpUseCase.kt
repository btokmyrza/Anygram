package kz.btokmyrza.anygram.feature.otp.domain.use.cases

import kotlinx.coroutines.flow.Flow
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthActionEntity
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthActionResult
import kz.btokmyrza.anygram.feature.auth.common.domain.repository.AuthRepository

class SendOtpUseCase(
    private val authRepository: AuthRepository,
) {

    operator fun invoke(code: String): Flow<AuthActionResult> =
        authRepository.sendAction(AuthActionEntity.SendOtp(code))
}
