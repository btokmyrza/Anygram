package kz.btokmyrza.library.core.domain.use.cases

import kz.btokmyrza.library.core.domain.repository.ThemeRepository

class SetDarkModeUseCase(
    private val themeRepository: ThemeRepository,
) {

    suspend operator fun invoke(enabled: Boolean) =
        themeRepository.setDarkMode(enabled)
}
