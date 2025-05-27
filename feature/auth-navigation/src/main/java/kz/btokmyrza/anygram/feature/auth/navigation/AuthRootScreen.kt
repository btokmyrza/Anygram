package kz.btokmyrza.anygram.feature.auth.navigation

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kz.btokmyrza.anygram.feature.auth.start.presentation.AuthStartScreen
import kz.btokmyrza.anygram.feature.phone.number.navigation.PhoneNumberRootScreen

@Composable
fun AuthRootScreen(component: AuthRootComponent) {
    val childStack by component.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(
            animator = fade(animationSpec = tween(durationMillis = 600)),
        ),
    ) { child ->
        when (val instance = child.instance) {
            is AuthRootComponent.Child.AuthStart -> {
                AuthStartScreen(component = instance.component)
            }
            is AuthRootComponent.Child.PhoneNumber -> {
                PhoneNumberRootScreen(component = instance.component)
            }
        }
    }
}
