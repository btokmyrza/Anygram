package kz.btokmyrza.anygram.feature.otp.presentation.mvi

import kotlinx.collections.immutable.toPersistentList
import kz.btokmyrza.anygram.feature.otp.presentation.model.OtpInputStateUiModel

object OtpReducer {

    fun getInitialState(): OtpUiState =
        OtpUiState(
            phoneNumber = "",
            codeType = "",
            nextCodeType = null,
            codeResendTimeout = 0,
            totalCode = List(5) { null }.toPersistentList(),
            codeLength = 5,
            focusedIndex = 0,
            inputState = OtpInputStateUiModel.Default,
        )

    fun OtpUiState.loaded(
        phoneNumber: String,
        codeType: String,
        nextCodeType: String?,
        codeResendTimeout: Int,
        codeLength: Int,
    ): OtpUiState =
        copy(
            phoneNumber = phoneNumber,
            codeType = codeType,
            nextCodeType = nextCodeType,
            codeResendTimeout = codeResendTimeout,
            totalCode = List(codeLength) { null }.toPersistentList(),
            codeLength = codeLength,
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

    fun OtpUiState.success(): OtpUiState =
        copy(inputState = OtpInputStateUiModel.Success)

    fun OtpUiState.error(): OtpUiState =
        copy(inputState = OtpInputStateUiModel.Error)

    fun OtpUiState.default(): OtpUiState =
        copy(
            totalCode = List(codeLength) { null }.toPersistentList(),
            focusedIndex = 0,
            inputState = OtpInputStateUiModel.Default,
        )
}
