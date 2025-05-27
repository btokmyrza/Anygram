package kz.btokmyrza.anygram.feature.phone.number.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.backStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import kotlinx.serialization.Serializable
import kz.btokmyrza.anygram.feature.country.chooser.domain.model.CountryChooserEntity
import kz.btokmyrza.anygram.feature.country.chooser.presentation.component.CountryChooserComponent
import kz.btokmyrza.anygram.feature.country.chooser.presentation.model.CountryChooserUiModel
import kz.btokmyrza.anygram.feature.otp.presentation.component.OtpComponent
import kz.btokmyrza.anygram.feature.phone.number.presentation.component.PhoneNumberComponent
import kz.btokmyrza.library.core.presentation.base.navigation.BaseComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class PhoneNumberRootComponent(
    componentContext: ComponentContext,
) : BaseComponent(componentContext), KoinComponent {

    private val navigation = StackNavigation<Config>()

    val childStack = childStack(
        source = navigation,
        serializer = null,
        initialConfiguration = Config.PhoneNumber,
        handleBackButton = true,
        childFactory = ::createChild,
    )

    private fun createChild(
        config: Config,
        componentContext: ComponentContext,
    ): Child =
        when (config) {
            Config.PhoneNumber -> {
                Child.PhoneNumber(
                    component = get<PhoneNumberComponent> {
                        parametersOf(
                            componentContext,
                            ::onNavigateToCountryChooser,
                            ::onNavigateToOtp,
                        )
                    },
                )
            }
            is Config.CountryChooser -> {
                Child.CountryChooser(
                    component = get<CountryChooserComponent> {
                        parametersOf(
                            componentContext,
                            config.countries,
                            ::onNavigateBackFromCountryChooser,
                        )
                    },
                )
            }
            is Config.Otp -> {
                Child.Otp(
                    component = get<OtpComponent> {
                        parametersOf(
                            componentContext,
                            ::onCodeEnterFinished,
                        )
                    },
                )
            }
        }

    private fun onNavigateToCountryChooser(countries: List<CountryChooserEntity>) {
        navigation.pushNew(Config.CountryChooser(countries))
    }

    private fun onNavigateToOtp() {
        navigation.pushNew(Config.Otp)
    }

    private fun onNavigateBackFromCountryChooser(selectedCountry: CountryChooserUiModel.Country?) {
        val previousChildNavState = childStack.backStack.lastOrNull { child ->
            child.configuration is Config.PhoneNumber
        }
        val previousChildNavStateInstance = previousChildNavState?.instance

        if (previousChildNavStateInstance is Child.PhoneNumber) {
            val phoneNumberComponent = previousChildNavStateInstance.component
            phoneNumberComponent.onCountryChosen(selectedCountry)
        }

        navigation.pop()
    }

    private fun onCodeEnterFinished() {

    }

    sealed class Child {

        class PhoneNumber(val component: PhoneNumberComponent) : Child()

        class CountryChooser(val component: CountryChooserComponent) : Child()

        class Otp(val component: OtpComponent) : Child()
    }

    @Serializable
    sealed class Config {

        @Serializable
        data object PhoneNumber : Config()

        @Serializable
        data class CountryChooser(val countries: List<CountryChooserEntity>) : Config()

        @Serializable
        data object Otp : Config()
    }
}
