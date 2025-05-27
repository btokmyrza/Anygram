package kz.btokmyrza.anygram.feature.otp.presentation.mvi

import kotlinx.collections.immutable.toPersistentList

object OtpReducer {

    fun getInitialState(codeLength: Int): OtpUiState =
        OtpUiState(
            totalCode = List(codeLength) { null }.toPersistentList(),
            codeLength = codeLength,
            focusedIndex = 0,
        )

    fun OtpUiState.fieldFocused(index: Int): OtpUiState =
        copy(focusedIndex = index)

    fun OtpUiState.totalCodeUpdated(
        totalCode: List<String?>,
        focusedIndex: Int,
    ): OtpUiState =
        copy(
            totalCode = totalCode.toPersistentList(),
            focusedIndex = focusedIndex,
        )
}
