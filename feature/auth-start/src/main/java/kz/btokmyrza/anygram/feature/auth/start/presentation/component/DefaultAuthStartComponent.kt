package kz.btokmyrza.anygram.feature.auth.start.presentation.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthStateEntity
import kz.btokmyrza.anygram.feature.auth.common.domain.use.cases.ObserveAuthUpdatesUseCase
import kz.btokmyrza.anygram.feature.auth.start.domain.use.cases.StartAuthUseCase
import kz.btokmyrza.anygram.feature.auth.start.presentation.mvi.AuthStartIntent
import kz.btokmyrza.anygram.feature.auth.start.presentation.mvi.AuthStartReducer
import kz.btokmyrza.anygram.feature.auth.start.presentation.mvi.AuthStartUiState
import kz.btokmyrza.library.core.domain.use.cases.ObserveThemeModeUseCase
import kz.btokmyrza.library.core.domain.use.cases.SetDarkModeUseCase
import kz.btokmyrza.library.core.presentation.base.navigation.BaseComponent

class DefaultAuthStartComponent(
    componentContext: ComponentContext,
    override val onNavigateToPhoneNumber: () -> Unit,
    observeThemeModeUseCase: ObserveThemeModeUseCase,
    observeAuthUpdatesUseCase: ObserveAuthUpdatesUseCase,
    private val setDarkModeUseCase: SetDarkModeUseCase,
    private val startAuthUseCase: StartAuthUseCase,
) : BaseComponent(componentContext), AuthStartComponent {

    override val uiState: StateFlow<AuthStartUiState> = combine(
        flow = observeThemeModeUseCase(),
        flow2 = observeAuthUpdatesUseCase().map { auth ->
            auth is AuthStateEntity.WaitingForAuthenticationStart
        },
    ) { isDarkModeEnabled, isStartMessagingEnabled ->
        AuthStartReducer.loaded(
            isDarkModeEnabled = isDarkModeEnabled,
            isStartMessagingEnabled = isStartMessagingEnabled,
        )
    }.stateIn(
        scope = this,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AuthStartReducer.getInitialState(),
    )

    override fun onIntent(intent: AuthStartIntent) {
        when (intent) {
            AuthStartIntent.OnDarkModeToggled -> {
                onDarkModeToggled()
            }
            AuthStartIntent.OnStartMessagingClicked -> {
                onStartMessagingClicked()
            }
        }
    }

    private fun onDarkModeToggled() {
        val newDarkModeState = !uiState.value.isDarkModeEnabled

        launch {
            setDarkModeUseCase(enabled = newDarkModeState)
        }
    }

    private fun onStartMessagingClicked() {
        startAuthUseCase().launchIn(this)
        onNavigateToPhoneNumber()
    }
}
