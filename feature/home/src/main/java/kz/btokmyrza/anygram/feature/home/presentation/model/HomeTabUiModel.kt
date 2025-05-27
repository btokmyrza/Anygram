package kz.btokmyrza.anygram.feature.home.presentation.model

sealed interface HomeTabUiModel {

    data object Contacts : HomeTabUiModel

    data object Calls : HomeTabUiModel

    data object Chats : HomeTabUiModel

    data object Settings : HomeTabUiModel
}
