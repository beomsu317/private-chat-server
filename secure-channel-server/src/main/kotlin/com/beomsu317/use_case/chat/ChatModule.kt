package com.beomsu317.use_case.chat

import org.koin.dsl.module

val chatUseCaseModule = module(createdAtStart = true) {
    single { RoomController() }
    single { ChatUseCase(get(), get()) }
}