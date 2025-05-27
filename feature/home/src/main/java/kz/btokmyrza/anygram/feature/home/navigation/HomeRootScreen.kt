package kz.btokmyrza.anygram.feature.home.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import kz.btokmyrza.anygram.feature.chats.presentation.ChatsScreen
import kz.btokmyrza.anygram.feature.home.presentation.model.HomeTabUiModel
import kz.btokmyrza.library.core.presentation.ui.theme.AnygramTheme

@Composable
fun HomeRootScreen(component: HomeRootComponent) {
    val childStack by component.childStack.subscribeAsState()
    val selectedTab by component.selectedTab.collectAsStateWithLifecycle()

    HomeRootScreenContent(
        childStack = childStack,
        selectedTab = selectedTab,
        onTabSelected = component::onTabSelected,
    )
}

@Composable
private fun HomeRootScreenContent(
    childStack: ChildStack<HomeRootComponent.Config, HomeRootComponent.Child>,
    selectedTab: HomeTabUiModel,
    onTabSelected: (HomeTabUiModel) -> Unit,
) {
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        content = { paddingValues ->
            HomeRootScreenMainContent(
                paddingValues = paddingValues,
                childStack = childStack,
            )
        },
        bottomBar = {
            HomeRootScreenBottomBar(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
            )
        },
    )
}

@Composable
private fun HomeRootScreenMainContent(
    paddingValues: PaddingValues,
    childStack: ChildStack<HomeRootComponent.Config, HomeRootComponent.Child>,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
    ) {
        Children(
            stack = childStack,
            animation = stackAnimation(fade()),
        ) { child ->
            when (val instance = child.instance) {
                is HomeRootComponent.Child.Contacts -> {
                    Text(text = "Contacts")
                }
                is HomeRootComponent.Child.Calls -> {
                    Text(text = "Calls")
                }
                is HomeRootComponent.Child.Chats -> {
                    ChatsScreen(chatsComponent = instance.component)
                }
                is HomeRootComponent.Child.Settings -> {
                    Text(text = "Settings")
                }
            }
        }
    }
}

@Composable
private fun HomeRootScreenBottomBar(
    selectedTab: HomeTabUiModel,
    onTabSelected: (HomeTabUiModel) -> Unit,
) {
    NavigationBar(
        containerColor = AnygramTheme.colors.background,
        tonalElevation = 2.dp,
    ) {
        listOf(
            Triple(HomeTabUiModel.Contacts, Icons.Default.Person, "Contacts"),
            Triple(HomeTabUiModel.Calls, Icons.Default.Call, "Calls"),
            Triple(HomeTabUiModel.Chats, Icons.Default.Create, "Chats"),
            Triple(HomeTabUiModel.Settings, Icons.Default.Settings, "Settings"),
        ).forEach { item ->
            NavigationBarItem(
                selected = selectedTab == item,
                icon = {
                    Icon(
                        imageVector = item.second,
                        contentDescription = item.third,
                    )
                },
                label = {
                    Text(text = item.third)
                },
                onClick = {
                    onTabSelected(item.first)
                },
            )
        }
    }
}
