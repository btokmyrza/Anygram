package kz.btokmyrza.library.core.data.di

import kz.btokmyrza.library.core.data.datasource.TdlibDataSource
import kz.btokmyrza.library.core.data.repository.DefaultThemeRepository
import kz.btokmyrza.library.core.data.settings.ThemeSettingsDataStore
import kz.btokmyrza.library.core.domain.repository.ThemeRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreDataModule = module {

    single { TdlibDataSource() }

    single { ThemeSettingsDataStore(context = androidContext()) }

    single<ThemeRepository> { DefaultThemeRepository(themeSettingsDataStore = get()) }
}
