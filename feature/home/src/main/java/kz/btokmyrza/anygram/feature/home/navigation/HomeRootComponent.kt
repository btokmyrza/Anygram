package kz.btokmyrza.anygram.feature.home.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import kz.btokmyrza.anygram.feature.chats.presentation.component.ChatsComponent
import kz.btokmyrza.anygram.feature.home.presentation.model.HomeTabUiModel
import kz.btokmyrza.library.core.presentation.base.navigation.BaseComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class HomeRootComponent(
    componentContext: ComponentContext,
) : BaseComponent(componentContext), KoinComponent {

    private val navigation = StackNavigation<Config>()

    private val _selectedTab = MutableStateFlow<HomeTabUiModel>(HomeTabUiModel.Chats)
    val selectedTab: StateFlow<HomeTabUiModel> = _selectedTab

    val childStack = childStack(
        source = navigation,
        serializer = null,
        initialConfiguration = Config.Chats,
        handleBackButton = false,
        childFactory = ::createChild,
    )

    fun onTabSelected(tab: HomeTabUiModel) {
        _selectedTab.update { tab }

        when (tab) {
            HomeTabUiModel.Contacts -> {
                navigation.bringToFront(Config.Contacts)
            }
            HomeTabUiModel.Calls -> {
                navigation.bringToFront(Config.Calls)
            }
            HomeTabUiModel.Chats -> {
                navigation.bringToFront(Config.Chats)
            }
            HomeTabUiModel.Settings -> {
                navigation.bringToFront(Config.Settings)
            }
        }
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext,
    ): Child =
        when (config) {
            is Config.Contacts -> {
                Child.Contacts()
            }
            is Config.Calls -> {
                Child.Calls()
            }
            is Config.Chats -> {
                Child.Chats(
                    component = get<ChatsComponent> {
                        parametersOf(componentContext)
                    },
                )
            }
            is Config.Settings -> {
                Child.Settings()
            }
        }

    sealed class Child {

        class Contacts() : Child()

        class Calls() : Child()

        class Chats(val component: ChatsComponent) : Child()

        class Settings() : Child()
    }

    @Serializable
    sealed class Config {

        @Serializable
        data object Contacts : Config()

        @Serializable
        data object Calls : Config()

        @Serializable
        data object Chats : Config()

        @Serializable
        data object Settings : Config()
    }
}
