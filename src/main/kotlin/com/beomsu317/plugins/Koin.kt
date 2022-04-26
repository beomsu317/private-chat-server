package com.beomsu317.plugins

import com.beomsu317.data.dataModules
import com.beomsu317.route.routeModules
import com.beomsu317.use_case.useCaseModules
import io.ktor.application.*
import org.koin.ktor.ext.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(modules)
    }
}

val modules = listOf(
    routeModules,
    dataModules,
    useCaseModules,
).flatten()