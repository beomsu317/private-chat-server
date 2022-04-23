package com.beomsu317.plugins

import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.request.*
import io.ktor.application.*
import io.ktor.response.*

fun Application.configureMonitoring() {
    install(CallLogging) {
        level = Level.DEBUG
        filter { call -> call.request.path().startsWith("/") }
    }
}
