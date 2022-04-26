package com.beomsu317.data.config

import com.beomsu317.data.user.UserRepositoryImpl
import com.beomsu317.use_case.user.UserRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val databaseModule = module(createdAtStart = true) {
    single {
        KMongo.createClient()
            .coroutine
            .getDatabase("secure_channel")
    }
}