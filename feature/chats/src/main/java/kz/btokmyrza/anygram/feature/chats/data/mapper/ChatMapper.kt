package kz.btokmyrza.anygram.feature.chats.data.mapper

import kz.btokmyrza.anygram.feature.chats.domain.model.ChatEntity
import org.drinkless.tdlib.TdApi

class ChatMapper(
    private val chatMessageContentMapper: ChatMessageContentMapper,
    private val chatTypeMapper: ChatTypeMapper,
) {

    fun toChatEntity(chat: TdApi.Chat): ChatEntity {
        val lastMessage = chat.lastMessage

        return ChatEntity(
            id = chat.id,
            title = chat.title,
            photoPath = getSmallPhotoPath(chat.photo),
            lastMessageContentType = chatMessageContentMapper
                .toChatMessageContentTypeEntity(lastMessage?.content),
            lastMessageTimestamp = lastMessage?.date ?: 0,
            unreadCount = chat.unreadCount,
            type = chatTypeMapper.toChatTypeEntity(chat.type),
        )
    }

    private fun getSmallPhotoPath(photoInfo: TdApi.ChatPhotoInfo?): String? {
        val smallPhotoLocal = photoInfo?.small?.local ?: return null

        return if (smallPhotoLocal.isDownloadingCompleted && smallPhotoLocal.path.isNotEmpty()) {
            smallPhotoLocal.path
        } else {
            null
        }
    }
}
