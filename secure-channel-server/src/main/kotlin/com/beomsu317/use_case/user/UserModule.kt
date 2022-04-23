package com.beomsu317.use_case.user

import org.koin.dsl.module

val userModule = module(createdAtStart = true) {
    single { RegisterUserUseCase(get()) }
    single { LoginUseCase(get()) }
    single { UploadImageUseCase(get()) }
    single { GetUserUseCase(get()) }
}