package kz.btokmyrza.anygram.feature.chats.di

import com.arkivanov.decompose.ComponentContext
import kz.btokmyrza.anygram.feature.chats.data.mapper.ChatMapper
import kz.btokmyrza.anygram.feature.chats.data.mapper.ChatMessageContentMapper
import kz.btokmyrza.anygram.feature.chats.data.mapper.ChatTypeMapper
import kz.btokmyrza.anygram.feature.chats.data.repository.DefaultChatsRepository
import kz.btokmyrza.anygram.feature.chats.domain.repository.ChatsRepository
import kz.btokmyrza.anygram.feature.chats.domain.use.cases.GetChatsUseCase
import kz.btokmyrza.anygram.feature.chats.presentation.component.ChatsComponent
import kz.btokmyrza.anygram.feature.chats.presentation.component.DefaultChatsComponent
import org.koin.dsl.module

val chatsModule = module {

    factory { ChatMessageContentMapper() }
    factory { ChatTypeMapper() }
    factory {
        ChatMapper(
            chatMessageContentMapper = get(),
            chatTypeMapper = get(),
        )
    }

    factory<ChatsRepository> {
        DefaultChatsRepository(
            tdlibDataSource = get(),
            chatMapper = get(),
        )
    }

    factory { GetChatsUseCase(chatsRepository = get()) }

    factory<ChatsComponent> { (
                                  componentContext: ComponentContext,
                              ) ->
        DefaultChatsComponent(
            componentContext = componentContext,
            getChatsUseCase = get(),
        )
    }
}
