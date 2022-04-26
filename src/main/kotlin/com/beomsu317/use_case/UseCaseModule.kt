package com.beomsu317.use_case

import com.beomsu317.use_case.chat.chatUseCaseModule
import com.beomsu317.use_case.user.userUseCaseModule

val useCaseModules = listOf(
    userUseCaseModule,
    chatUseCaseModule
)