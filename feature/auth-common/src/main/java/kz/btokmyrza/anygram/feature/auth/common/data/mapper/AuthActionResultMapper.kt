package kz.btokmyrza.anygram.feature.auth.common.data.mapper

import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthActionResultEntity
import org.drinkless.tdlib.TdApi

class AuthActionResultMapper {

    fun toAuthActionResult(result: TdApi.Object): AuthActionResultEntity =
        when (result) {
            is TdApi.Ok -> {
                AuthActionResultEntity.Success
            }
            is TdApi.Error -> {
                AuthActionResultEntity.Error(result.message)
            }
            else -> {
                throw IllegalArgumentException("Unknown result type: $result")
            }
        }
}
