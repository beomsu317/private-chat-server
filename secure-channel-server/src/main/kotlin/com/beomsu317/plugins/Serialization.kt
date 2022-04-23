package com.beomsu317.plugins

import io.ktor.serialization.*
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import kotlinx.serialization.json.Json

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Json {
            explicitNulls = false
            ignoreUnknownKeys = true
        })
    }

//    routing {
//        get("/json/kotlinx-serialization") {
//                call.respond(mapOf("hello" to "world"))
//            }
//    }
}
