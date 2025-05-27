package kz.btokmyrza.library.core.domain.use.cases

import kotlinx.coroutines.flow.Flow
import kz.btokmyrza.library.core.domain.repository.ThemeRepository

class ObserveThemeModeUseCase(
    private val themeRepository: ThemeRepository,
) {

    operator fun invoke(): Flow<Boolean> =
        themeRepository.isDarkMode
}
