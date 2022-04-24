package com.beomsu317.data.chat

import com.beomsu317.use_case.chat.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val roomDataModule = module(createdAtStart = true) {
    single<RoomRepository> {
        RoomRepositoryImpl(get(), Dispatchers.IO)
    }
}