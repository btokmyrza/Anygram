package kz.btokmyrza.anygram.feature.auth.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import kz.btokmyrza.anygram.feature.auth.start.presentation.component.AuthStartComponent
import kz.btokmyrza.anygram.feature.phone.number.navigation.PhoneNumberRootComponent
import kz.btokmyrza.library.core.presentation.base.navigation.BaseComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class AuthRootComponent(
    componentContext: ComponentContext,
    private val onNavigateToHome: () -> Unit,
) : BaseComponent(componentContext), KoinComponent {

    private val navigation = StackNavigation<Config>()

    val childStack = childStack(
        source = navigation,
        serializer = null,
        initialConfiguration = Config.AuthStart,
        handleBackButton = true,
        childFactory = ::createChild,
    )

    private fun createChild(
        config: Config,
        componentContext: ComponentContext,
    ): Child =
        when (config) {
            Config.AuthStart -> {
                Child.AuthStart(
                    component = get<AuthStartComponent> {
                        parametersOf(
                            componentContext,
                            { navigation.pushNew(Config.PhoneNumber) },
                        )
                    },
                )
            }
            Config.PhoneNumber -> {
                Child.PhoneNumber(
                    component = get<PhoneNumberRootComponent> {
                        parametersOf(
                            componentContext,
                            ::navigateToHome,
                        )
                    },
                )
            }
        }

    private fun navigateToHome() {
        onNavigateToHome()
    }

    sealed class Child {

        class AuthStart(val component: AuthStartComponent) : Child()

        class PhoneNumber(val component: PhoneNumberRootComponent) : Child()
    }

    sealed class Config {

        data object AuthStart : Config()

        data object PhoneNumber : Config()
    }
}
