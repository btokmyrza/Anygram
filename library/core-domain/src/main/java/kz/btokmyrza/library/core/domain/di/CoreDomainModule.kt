package kz.btokmyrza.library.core.domain.di

import kz.btokmyrza.library.core.domain.use.cases.ObserveThemeModeUseCase
import kz.btokmyrza.library.core.domain.use.cases.SetDarkModeUseCase
import org.koin.dsl.module

val coreDomainModule = module {

    factory { ObserveThemeModeUseCase(themeRepository = get()) }

    factory { SetDarkModeUseCase(themeRepository = get()) }
}