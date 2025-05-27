package kz.btokmyrza.anygram.feature.auth.common.data.mapper

import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthActionResult
import org.drinkless.tdlib.TdApi

class AuthActionResultMapper {

    fun toAuthActionResult(result: TdApi.Object): AuthActionResult =
        when (result) {
            is TdApi.Ok -> {
                AuthActionResult.Success
            }
            is TdApi.Error -> {
                AuthActionResult.Error(result.message)
            }
            else -> {
                throw IllegalArgumentException("Unknown result type: $result")
            }
        }
}
