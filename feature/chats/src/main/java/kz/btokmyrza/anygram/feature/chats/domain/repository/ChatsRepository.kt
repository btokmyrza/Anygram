package kz.btokmyrza.anygram.feature.chats.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.btokmyrza.anygram.feature.chats.domain.model.ChatEntity

interface ChatsRepository {

    fun getChats(): Flow<List<ChatEntity>>
}
