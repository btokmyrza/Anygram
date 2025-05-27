package kz.btokmyrza.anygram.feature.chats.domain.model

data class ChatEntity(
    val id: Long,
    val title: String,
    val photoPath: String?,
    val lastMessageContentType: ChatMessageContentTypeEntity,
    val lastMessageTimestamp: Int,
    val unreadCount: Int,
    val type: ChatTypeEntity,
)
