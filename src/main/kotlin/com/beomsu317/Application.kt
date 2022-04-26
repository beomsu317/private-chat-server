package com.beomsu317

import io.ktor.application.*
import com.beomsu317.plugins.*
import kotlinx.coroutines.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureKoin()
    configureAuthentication()
    configureSockets()
    configurateStatusPages()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}