package kz.btokmyrza.anygram.feature.phone.number.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kz.btokmyrza.anygram.feature.country.chooser.presentation.CountryChooserScreen
import kz.btokmyrza.anygram.feature.otp.presentation.OtpScreen
import kz.btokmyrza.anygram.feature.phone.number.presentation.PhoneNumberScreen

@Composable
fun PhoneNumberRootScreen(component: PhoneNumberRootComponent) {
    val childStack by component.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(fade().plus(slide())),
    ) { child ->
        when (val instance = child.instance) {
            is PhoneNumberRootComponent.Child.PhoneNumber -> {
                PhoneNumberScreen(component = instance.component)
            }
            is PhoneNumberRootComponent.Child.CountryChooser -> {
                CountryChooserScreen(component = instance.component)
            }
            is PhoneNumberRootComponent.Child.Otp -> {
                OtpScreen(component = instance.component)
            }
        }
    }
}
