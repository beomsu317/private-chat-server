package com.beomsu317.data.message

import com.beomsu317.use_case.chat.repository.MessageRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val messageDataModule = module(createdAtStart = true) {
    single<MessageRepository> { MessageRepositoryImpl(get(), Dispatchers.IO) }
}