package com.beomsu317.use_case.chat

import com.beomsu317.use_case.chat.controller.NotificationController
import com.beomsu317.use_case.chat.controller.RoomController
import org.koin.dsl.module

val chatUseCaseModule = module(createdAtStart = true) {
    single { RoomController() }
    single { ChatUseCase(get(), get(), get()) }
    single { JoinRoomUseCase(get(), get(), get()) }
    single { CreateRoomUseCase(get(), get(), get()) }
    single { LeaveRoomUseCase(get(), get(), get()) }
    single { ChatServiceUseCase(get(), get()) }
    single { NotificationController() }
}