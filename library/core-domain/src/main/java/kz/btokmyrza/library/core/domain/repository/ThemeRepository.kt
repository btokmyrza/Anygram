package kz.btokmyrza.library.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface ThemeRepository {

    val isDarkMode: Flow<Boolean>

    suspend fun setDarkMode(enabled: Boolean)
}
