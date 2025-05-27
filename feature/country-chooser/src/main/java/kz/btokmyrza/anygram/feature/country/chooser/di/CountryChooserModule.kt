package kz.btokmyrza.anygram.feature.country.chooser.di

import com.arkivanov.decompose.ComponentContext
import kz.btokmyrza.anygram.feature.country.chooser.presentation.component.CountryChooserComponent
import kz.btokmyrza.anygram.feature.country.chooser.presentation.component.DefaultCountryChooserComponent
import kz.btokmyrza.anygram.feature.country.chooser.presentation.model.CountryChooserUiModel
import org.koin.dsl.module

val countryChooserModule = module {

    factory<CountryChooserComponent> { (
                                           componentContext: ComponentContext,
                                           countries: List<CountryChooserUiModel>,
                                           onNavigateBack: (CountryChooserUiModel.Country?) -> Unit,
                                       ) ->
        DefaultCountryChooserComponent(
            componentContext = componentContext,
            onNavigateBack = onNavigateBack,
            countries = countries,
        )
    }
}
