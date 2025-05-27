package kz.btokmyrza.anygram.feature.chats.data.repository

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kz.btokmyrza.anygram.feature.chats.data.mapper.ChatMapper
import kz.btokmyrza.anygram.feature.chats.domain.model.ChatEntity
import kz.btokmyrza.anygram.feature.chats.domain.repository.ChatsRepository
import kz.btokmyrza.library.core.data.datasource.TdlibDataSource
import org.drinkless.tdlib.TdApi
import java.util.concurrent.ConcurrentHashMap

class DefaultChatsRepository(
    private val tdlibDataSource: TdlibDataSource,
    private val chatMapper: ChatMapper,
) : ChatsRepository {

    private val chatsCache = ConcurrentHashMap<Long, TdApi.Chat>()

    override fun getChats(): Flow<List<ChatEntity>> =
        callbackFlow {
            val updatesJob = tdlibDataSource.updates
                .onEach { update ->
                    if (!processUpdateAndCheckIfChanged(update)) {
                        return@onEach
                    }

                    val currentChats = chatsCache.values.toList()
                    val sortedChats = currentChats
                        .filter { chat ->
                            chat.positions
                                .any { chatPosition ->
                                    chatPosition.list is TdApi.ChatListMain
                                }
                        }
                        .sortedByDescending { chat ->
                            chat.positions
                                .find { chatPosition ->
                                    chatPosition.list is TdApi.ChatListMain
                                }
                                ?.order
                                ?: 0L
                        }
                        .map(chatMapper::toChatEntity)

                    trySend(sortedChats)
                }
                .launchIn(this)

            launch {
                val loadChats = TdApi.LoadChats().apply {
                    chatList = TdApi.ChatListMain()
                    limit = 100
                }
                tdlibDataSource.sendAsync(loadChats)
            }

            awaitClose { updatesJob.cancel() }
        }

    private fun processUpdateAndCheckIfChanged(update: TdApi.Object): Boolean =
        when (update) {
            is TdApi.UpdateNewChat -> {
                chatsCache[update.chat.id] = update.chat
                true
            }
            is TdApi.UpdateChatTitle -> {
                chatsCache.computeIfPresent(update.chatId) { _, chat ->
                    chat.apply { title = update.title }
                } != null
            }
            is TdApi.UpdateChatPhoto -> {
                chatsCache.computeIfPresent(update.chatId) { _, chat ->
                    chat.apply { photo = update.photo }
                } != null
            }
            is TdApi.UpdateChatLastMessage -> {
                chatsCache.computeIfPresent(update.chatId) { _, chat ->
                    chat.apply {
                        lastMessage = update.lastMessage
                        positions = update.positions
                    }
                } != null
            }
            is TdApi.UpdateChatPosition -> {
                chatsCache.computeIfPresent(update.chatId) { _, chat ->
                    val otherPositions = chat.positions.filterNot { chatPosition ->
                        chatPosition.list.constructor == update.position.list.constructor
                    }
                    val newPositions = if (update.position.order != 0L) {
                        otherPositions + update.position
                    } else {
                        otherPositions
                    }

                    chat.apply { positions = newPositions.toTypedArray() }
                } != null
            }
            is TdApi.UpdateChatReadInbox -> {
                chatsCache.computeIfPresent(update.chatId) { _, chat ->
                    chat.apply {
                        unreadCount = update.unreadCount
                        lastReadInboxMessageId = update.lastReadInboxMessageId
                    }
                } != null
            }
            else -> {
                false
            }
        }
}
