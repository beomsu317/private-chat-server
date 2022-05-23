package com.beomsu317.use_case.chat

import com.beomsu317.use_case.chat.controller.UserSessionController
import org.koin.dsl.module

val chatUseCaseModule = module(createdAtStart = true) {
    single { UserSessionController() }
    single { ChatUseCase(get(), get(), get()) }
    single { JoinRoomUseCase(get(), get(), get()) }
    single { CreateRoomUseCase(get(), get()) }
    single { LeaveRoomUseCase(get(), get(), get()) }
    single { ChatServiceUseCase(get()) }
}