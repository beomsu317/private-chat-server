package com.beomsu317.data.user

import com.beomsu317.use_case.user.UserRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val userDataModule = module(createdAtStart = true) {
    single<UserRepository> {
        UserRepositoryImpl(get(), Dispatchers.IO)
    }
}