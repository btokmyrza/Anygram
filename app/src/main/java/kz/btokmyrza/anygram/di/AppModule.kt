package kz.btokmyrza.anygram.di

import com.arkivanov.decompose.ComponentContext
import kz.btokmyrza.anygram.presentation.AppRootComponent
import org.koin.dsl.module

val appModule = module {

    factory { (componentContext: ComponentContext) ->
        AppRootComponent(
            componentContext = componentContext,
            observeThemeModeUseCase = get(),
            observeAuthUpdatesUseCase = get(),
        )
    }
}
