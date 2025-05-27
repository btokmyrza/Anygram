package kz.btokmyrza.anygram.feature.chats.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kz.btokmyrza.anygram.feature.chats.domain.repository.ChatsRepository
import kz.btokmyrza.library.core.data.datasource.TdlibDataSource
import org.drinkless.tdlib.TdApi

class DefaultChatsRepository(
    private val tdlibDataSource: TdlibDataSource,
) : ChatsRepository {

    override fun loadChats() {
        tdlibDataSource.sendAsync(TdApi.LoadChats())
    }

    override fun observeUpdates(): Flow<TdApi.UpdateNewChat> =
        tdlibDataSource.updates
            .filterIsInstance<TdApi.UpdateNewChat>()
}
