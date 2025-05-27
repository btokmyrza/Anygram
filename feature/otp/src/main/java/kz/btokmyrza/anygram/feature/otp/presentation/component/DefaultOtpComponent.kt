package kz.btokmyrza.anygram.feature.otp.presentation.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthActionResultEntity
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthCodeInfoEntity
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthStateEntity
import kz.btokmyrza.anygram.feature.auth.common.domain.use.cases.ObserveAuthUpdatesUseCase
import kz.btokmyrza.anygram.feature.otp.domain.use.cases.SendOtpUseCase
import kz.btokmyrza.anygram.feature.otp.presentation.mvi.OtpIntent
import kz.btokmyrza.anygram.feature.otp.presentation.mvi.OtpReducer
import kz.btokmyrza.anygram.feature.otp.presentation.mvi.OtpReducer.default
import kz.btokmyrza.anygram.feature.otp.presentation.mvi.OtpReducer.error
import kz.btokmyrza.anygram.feature.otp.presentation.mvi.OtpReducer.fieldFocused
import kz.btokmyrza.anygram.feature.otp.presentation.mvi.OtpReducer.loaded
import kz.btokmyrza.anygram.feature.otp.presentation.mvi.OtpReducer.success
import kz.btokmyrza.anygram.feature.otp.presentation.mvi.OtpReducer.totalCodeUpdated
import kz.btokmyrza.anygram.feature.otp.presentation.mvi.OtpUiState
import kz.btokmyrza.library.core.presentation.base.navigation.BaseComponent
import kz.btokmyrza.library.core.presentation.extensions.updateUiState

class DefaultOtpComponent(
    componentContext: ComponentContext,
    override val onNavigateToHome: () -> Unit,
    override val onNavigateBack: () -> Unit,
    observeAuthUpdatesUseCase: ObserveAuthUpdatesUseCase,
    private val sendOtpUseCase: SendOtpUseCase,
) : BaseComponent(componentContext), OtpComponent {

    private val _uiState = MutableStateFlow(OtpReducer.getInitialState())
    override val uiState: StateFlow<OtpUiState> = _uiState

    init {
        observeAuthUpdatesUseCase()
            .filterIsInstance<AuthStateEntity.WaitingForOtp>()
            .onEach { authStateWaitingForOtp ->
                when (val codeType = authStateWaitingForOtp.codeType) {
                    is AuthCodeInfoEntity.TelegramMessage -> {
                        _uiState.updateUiState {
                            loaded(
                                phoneNumber = authStateWaitingForOtp.phoneNumber,
                                codeType = codeType.toString(),
                                nextCodeType = authStateWaitingForOtp.nextCodeType?.toString(),
                                codeResendTimeout = authStateWaitingForOtp.codeResendTimeout,
                                codeLength = codeType.length,
                            )
                        }
                    }
                    else -> {
                        Unit
                    }
                }
            }
            .launchIn(this)
    }

    override fun onIntent(intent: OtpIntent) {
        when (intent) {
            is OtpIntent.OnFieldFocused -> {
                onFieldFocused(intent.index)
            }
            is OtpIntent.OnCodeChanged -> {
                onCodeChanged(intent.code)
            }
            is OtpIntent.OnCodeDeleted -> {
                onCodeDeleted()
            }
            OtpIntent.OnBackClicked -> {
                onBackClicked()
            }
        }
    }

    private fun onFieldFocused(index: Int) {
        _uiState.updateUiState { fieldFocused(index) }
    }

    private fun onCodeChanged(code: String) {
        val uiState = _uiState.value
        val totalCode = uiState.totalCode
        val focusedIndex = uiState.focusedIndex

        val updatedTotalCode = totalCode.mapIndexed { index, currentCode ->
            if (index == focusedIndex) {
                code
            } else {
                currentCode
            }
        }
        _uiState.updateUiState {
            totalCodeUpdated(
                totalCode = updatedTotalCode,
                focusedIndex = getUpdatedFocusedIndex(focusedIndex + 1),
            )
        }

        val updatedCode = updatedTotalCode.joinToString("")

        if (updatedCode.length == uiState.codeLength) {
            sendOtpUseCase(updatedCode)
                .onEach { result ->
                    when (result) {
                        AuthActionResultEntity.Success -> {
                            _uiState.updateUiState { success() }
                            delay(1000L)
                            onNavigateToHome()
                        }
                        is AuthActionResultEntity.Error -> {
                            _uiState.updateUiState { error() }
                            delay(1000L)
                            _uiState.updateUiState { default() }
                        }
                    }
                }
                .launchIn(this)
        }
    }

    private fun onCodeDeleted() {
        val uiState = _uiState.value
        val totalCode = uiState.totalCode
        val focusedIndex = uiState.focusedIndex

        val updatedTotalCode = totalCode.mapIndexed { index, currentCode ->
            if (index == focusedIndex) {
                null
            } else {
                currentCode
            }
        }
        _uiState.updateUiState {
            totalCodeUpdated(
                totalCode = updatedTotalCode,
                focusedIndex = getUpdatedFocusedIndex(focusedIndex - 1),
            )
        }
    }

    private fun getUpdatedFocusedIndex(focusedIndex: Int): Int {
        val uiState = _uiState.value

        return focusedIndex.coerceIn(
            minimumValue = 0,
            maximumValue = uiState.codeLength - 1,
        )
    }

    private fun onBackClicked() {
        onNavigateBack()
    }
}
