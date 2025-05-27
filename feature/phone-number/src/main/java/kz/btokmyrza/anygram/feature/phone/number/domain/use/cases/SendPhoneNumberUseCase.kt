package kz.btokmyrza.anygram.feature.phone.number.domain.use.cases

import kotlinx.coroutines.flow.Flow
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthActionEntity
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthActionResult
import kz.btokmyrza.anygram.feature.auth.common.domain.repository.AuthRepository

class SendPhoneNumberUseCase(
    private val authRepository: AuthRepository,
) {

    operator fun invoke(phoneNumber: String): Flow<AuthActionResult> =
        authRepository.sendAction(AuthActionEntity.SendPhoneNumber(phoneNumber))
}