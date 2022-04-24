package com.beomsu317.data.chat

import com.beomsu317.use_case.chat.ChatRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val chatDataModule = module(createdAtStart = true) {
    single<ChatRepository> {
        ChatRepositoryImpl(get(), Dispatchers.IO)
    }
}