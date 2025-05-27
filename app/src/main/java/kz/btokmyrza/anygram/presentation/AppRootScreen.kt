package kz.btokmyrza.anygram.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kz.btokmyrza.anygram.feature.auth.navigation.AuthRootScreen
import kz.btokmyrza.anygram.feature.home.navigation.HomeRootScreen

@Composable
fun AppRootScreen(component: AppRootComponent) {
    val childStack by component.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(fade().plus(scale())),
    ) { child ->
        when (val instance = child.instance) {
            is AppRootComponent.Child.Auth -> {
                AuthRootScreen(component = instance.component)
            }
            is AppRootComponent.Child.Home -> {
                HomeRootScreen(component = instance.component)
            }
        }
    }
}
