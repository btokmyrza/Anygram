package kz.btokmyrza.library.core.data.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.themeSettingsDataStore by preferencesDataStore(name = "theme_settings")

class ThemeSettingsDataStore(private val context: Context) {

    val isDarkMode: Flow<Boolean> = context.themeSettingsDataStore.data.map { prefs ->
        prefs[IS_DARK_MODE] == true
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.themeSettingsDataStore.edit { prefs ->
            prefs[IS_DARK_MODE] = enabled
        }
    }

    private companion object {

        val IS_DARK_MODE = booleanPreferencesKey("dark_mode")
    }
}
