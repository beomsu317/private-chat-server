package com.beomsu317.plugins

import com.beomsu317.route.Route
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.content.*
import io.ktor.routing.*
import org.koin.core.context.GlobalContext
import java.io.File

fun Application.configureRouting() {
    val routes = GlobalContext.get().getAll<Route>().toSet()
    log.info("registered route. {}", routes.map { it.javaClass.simpleName })
    routes.forEach { route -> route(this@configureRouting) }

    routing {
        static("/user/profile") {
            staticRootFolder = File("uploads/user/profile")
            files(".")
        }
    }
}
