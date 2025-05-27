package kz.btokmyrza.anygram.feature.chats.domain.model

sealed interface ChatTypeEntity {

    data object Private : ChatTypeEntity

    data object BasicGroup : ChatTypeEntity

    data object Supergroup : ChatTypeEntity

    data object Secret : ChatTypeEntity

    data object Unknown : ChatTypeEntity
}
