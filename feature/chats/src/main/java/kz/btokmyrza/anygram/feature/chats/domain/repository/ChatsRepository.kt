package kz.btokmyrza.anygram.feature.chats.domain.repository

import kotlinx.coroutines.flow.Flow
import org.drinkless.tdlib.TdApi

interface ChatsRepository {

    fun loadChats()

    fun observeUpdates(): Flow<TdApi.UpdateNewChat>
}
