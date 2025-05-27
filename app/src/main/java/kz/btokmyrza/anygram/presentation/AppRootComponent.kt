package kz.btokmyrza.anygram.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.Serializable
import kz.btokmyrza.anygram.feature.auth.navigation.AuthRootComponent
import kz.btokmyrza.library.core.domain.use.cases.ObserveThemeModeUseCase
import kz.btokmyrza.library.core.presentation.base.navigation.BaseComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class AppRootComponent(
    componentContext: ComponentContext,
    observeThemeModeUseCase: ObserveThemeModeUseCase,
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

    private fun createChild(
        config: Config,
        componentContext: ComponentContext,
    ): Child =
        when (config) {
            is Config.Auth -> Child.Auth(
                component = get<AuthRootComponent> {
                    parametersOf(componentContext)
                }
            )
            is Config.Main -> {
                TODO()
            }
        }

    private fun navigateToMainModule() {
        navigation.replaceAll(Config.Main)
    }

    sealed class Child {

        class Auth(val component: AuthRootComponent) : Child()

        class Main() : Child()
    }

    @Serializable
    sealed class Config {

        @Serializable
        data object Auth : Config()

        @Serializable
        data object Main : Config()
    }
}
