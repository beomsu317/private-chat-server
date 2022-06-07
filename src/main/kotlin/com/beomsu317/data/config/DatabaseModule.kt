package com.beomsu317.data.config

import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val databaseModule = module(createdAtStart = true) {
    single {
        KMongo.createClient()
            .coroutine
            .getDatabase("private_chat")
    }
}