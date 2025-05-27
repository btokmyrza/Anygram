package kz.btokmyrza.anygram.feature.phone.number.di

import com.arkivanov.decompose.ComponentContext
import kz.btokmyrza.anygram.feature.country.chooser.domain.model.CountryChooserEntity
import kz.btokmyrza.anygram.feature.phone.number.data.mapper.CountryMapper
import kz.btokmyrza.anygram.feature.phone.number.data.mapper.PhoneMapper
import kz.btokmyrza.anygram.feature.phone.number.data.repository.DefaultCountryRepository
import kz.btokmyrza.anygram.feature.phone.number.domain.repository.CountryRepository
import kz.btokmyrza.anygram.feature.phone.number.domain.use.cases.FindCountryByPhoneCodeUseCase
import kz.btokmyrza.anygram.feature.phone.number.domain.use.cases.GetCountriesUseCase
import kz.btokmyrza.anygram.feature.phone.number.domain.use.cases.GetCurrentLocationCountryUseCase
import kz.btokmyrza.anygram.feature.phone.number.domain.use.cases.GetPhoneNumberMaskUseCase
import kz.btokmyrza.anygram.feature.phone.number.domain.use.cases.SendPhoneNumberUseCase
import kz.btokmyrza.anygram.feature.phone.number.navigation.PhoneNumberRootComponent
import kz.btokmyrza.anygram.feature.phone.number.presentation.component.DefaultPhoneNumberComponent
import kz.btokmyrza.anygram.feature.phone.number.presentation.component.PhoneNumberComponent
import kz.btokmyrza.anygram.feature.phone.number.presentation.mapper.CountryUiMapper
import org.koin.dsl.module

val phoneNumberModule = module {

    factory { CountryMapper() }
    factory { PhoneMapper() }

    single<CountryRepository> {
        DefaultCountryRepository(
            tdlibDataSource = get(),
            countryMapper = get(),
            phoneMapper = get(),
        )
    }

    factory {
        GetCurrentLocationCountryUseCase(
            countryRepository = get(),
        )
    }
    factory {
        GetPhoneNumberMaskUseCase(
            countryRepository = get(),
        )
    }
    factory {
        FindCountryByPhoneCodeUseCase(
            countryRepository = get(),
        )
    }
    factory {
        SendPhoneNumberUseCase(
            authRepository = get(),
        )
    }
    factory {
        GetCountriesUseCase(
            countryRepository = get(),
        )
    }

    factory { CountryUiMapper() }
    factory { (
                  componentContext: ComponentContext,
                  onNavigateToHome: () -> Unit,
              ) ->
        PhoneNumberRootComponent(
            componentContext = componentContext,
            onNavigateToHome = onNavigateToHome,
        )
    }
    factory<PhoneNumberComponent> { (
                                        componentContext: ComponentContext,
                                        onNavigateToCountryChooser: (List<CountryChooserEntity>) -> Unit,
                                        onNavigateToOtp: () -> Unit,
                                    ) ->
        DefaultPhoneNumberComponent(
            componentContext = componentContext,
            onNavigateToCountryChooser = onNavigateToCountryChooser,
            onNavigateToOtp = onNavigateToOtp,
            countryUiMapper = get(),
            getCurrentLocationCountryUseCase = get(),
            getCountriesUseCase = get(),
            getPhoneNumberMaskUseCase = get(),
            findCountryByPhoneCodeUseCase = get(),
            sendPhoneNumberUseCase = get(),
        )
    }
}
