package kz.btokmyrza.anygram.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthStateEntity
import kz.btokmyrza.anygram.feature.auth.common.domain.use.cases.ObserveAuthUpdatesUseCase
import kz.btokmyrza.anygram.feature.auth.navigation.AuthRootComponent
import kz.btokmyrza.anygram.feature.home.navigation.HomeRootComponent
import kz.btokmyrza.library.core.domain.use.cases.ObserveThemeModeUseCase
import kz.btokmyrza.library.core.presentation.base.navigation.BaseComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class AppRootComponent(
    componentContext: ComponentContext,
    observeThemeModeUseCase: ObserveThemeModeUseCase,
    observeAuthUpdatesUseCase: ObserveAuthUpdatesUseCase,
) : BaseComponent(componentContext), KoinComponent {

    private val navigation = StackNavigation<Config>()

    val childStack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Auth,
        handleBackButton = true,
        childFactory = ::createChild,
    )

    val isDarkModeEnabled = observeThemeModeUseCase()
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = false,
        )

    init {
        launch {
            observeAuthUpdatesUseCase()
                .distinctUntilChanged()
                .collectLatest { authState ->
                    when (authState) {
                        is AuthStateEntity.Authenticated -> {
                            navigation.replaceAll(Config.Home)
                        }
                        else -> {
                            navigation.replaceAll(Config.Auth)
                        }
                    }
                }
        }
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext,
    ): Child =
        when (config) {
            is Config.Auth -> {
                Child.Auth(
                    component = get<AuthRootComponent> {
                        parametersOf(
                            componentContext,
                            ::navigateToHome,
                        )
                    },
                )
            }
            is Config.Home -> {
                Child.Home(
                    component = get<HomeRootComponent> {
                        parametersOf(componentContext)
                    },
                )
            }
        }

    private fun navigateToHome() {
        navigation.replaceAll(Config.Home)
    }

    sealed class Child {

        class Auth(val component: AuthRootComponent) : Child()

        class Home(val component: HomeRootComponent) : Child()
    }

    @Serializable
    sealed class Config {

        @Serializable
        data object Auth : Config()

        @Serializable
        data object Home : Config()
    }
}
