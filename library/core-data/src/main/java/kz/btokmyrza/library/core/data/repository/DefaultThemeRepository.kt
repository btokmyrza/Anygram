package kz.btokmyrza.library.core.data.repository

import kotlinx.coroutines.flow.Flow
import kz.btokmyrza.library.core.data.settings.ThemeSettingsDataStore
import kz.btokmyrza.library.core.domain.repository.ThemeRepository

class DefaultThemeRepository(
    private val themeSettingsDataStore: ThemeSettingsDataStore,
) : ThemeRepository {

    override val isDarkMode: Flow<Boolean> =
        themeSettingsDataStore.isDarkMode

    override suspend fun setDarkMode(enabled: Boolean) {
        themeSettingsDataStore.setDarkMode(enabled)
    }
}
