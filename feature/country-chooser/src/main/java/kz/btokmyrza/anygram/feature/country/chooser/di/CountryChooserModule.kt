package kz.btokmyrza.anygram.feature.country.chooser.di

import com.arkivanov.decompose.ComponentContext
import kz.btokmyrza.anygram.feature.country.chooser.domain.model.CountryChooserEntity
import kz.btokmyrza.anygram.feature.country.chooser.domain.use.cases.SearchCountriesUseCase
import kz.btokmyrza.anygram.feature.country.chooser.presentation.component.CountryChooserComponent
import kz.btokmyrza.anygram.feature.country.chooser.presentation.component.DefaultCountryChooserComponent
import kz.btokmyrza.anygram.feature.country.chooser.presentation.mapper.CountryChooserUiMapper
import kz.btokmyrza.anygram.feature.country.chooser.presentation.model.CountryChooserUiModel
import org.koin.dsl.module

val countryChooserModule = module {

    factory { SearchCountriesUseCase() }

    factory { CountryChooserUiMapper() }

    factory<CountryChooserComponent> { (
                                           componentContext: ComponentContext,
                                           countries: List<CountryChooserEntity>,
                                           onNavigateBack: (CountryChooserUiModel.Country?) -> Unit,
                                       ) ->
        DefaultCountryChooserComponent(
            componentContext = componentContext,
            onNavigateBack = onNavigateBack,
            countryChooserUiMapper = get(),
            searchCountriesUseCase = get(),
            countries = countries,
        )
    }
}
