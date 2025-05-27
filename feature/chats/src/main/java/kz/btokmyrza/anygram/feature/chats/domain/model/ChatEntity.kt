package kz.btokmyrza.anygram.feature.chats.domain.model

data class ChatEntity(
    val id: Long,
    val type: String,
    val title: String,
    val photo: String?, // TODO
    val lastMessage: String?,
)
