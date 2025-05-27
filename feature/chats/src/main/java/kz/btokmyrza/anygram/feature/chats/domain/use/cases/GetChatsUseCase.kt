package kz.btokmyrza.anygram.feature.chats.domain.use.cases

import kotlinx.coroutines.flow.Flow
import kz.btokmyrza.anygram.feature.chats.domain.model.ChatEntity
import kz.btokmyrza.anygram.feature.chats.domain.repository.ChatsRepository

class GetChatsUseCase(
    private val chatsRepository: ChatsRepository,
) {

    operator fun invoke(): Flow<List<ChatEntity>> =
        chatsRepository.getChats()
}
