package com.beomsu317.route

import io.ktor.application.*
import io.ktor.routing.*

abstract class Route(val route: Routing.() -> Unit) {
    operator fun invoke(app: Application) = app.routing {
        route()
    }
}