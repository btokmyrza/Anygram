package kz.btokmyrza.anygram.feature.chats.data.mapper

import kz.btokmyrza.anygram.feature.chats.domain.model.ChatTypeEntity
import org.drinkless.tdlib.TdApi

class ChatTypeMapper {

    fun toChatTypeEntity(chatType: TdApi.ChatType): ChatTypeEntity =
        when (chatType) {
            is TdApi.ChatTypePrivate -> {
                ChatTypeEntity.Private
            }
            is TdApi.ChatTypeBasicGroup -> {
                ChatTypeEntity.BasicGroup
            }
            is TdApi.ChatTypeSupergroup -> {
                ChatTypeEntity.Supergroup
            }
            is TdApi.ChatTypeSecret -> {
                ChatTypeEntity.Secret
            }
            else -> {
                ChatTypeEntity.Unknown
            }
        }
}
