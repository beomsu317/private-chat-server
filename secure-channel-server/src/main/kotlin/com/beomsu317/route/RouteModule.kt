package com.beomsu317.route

import com.beomsu317.route.user.ProfileRoute
import com.beomsu317.route.user.LoginRoute
import com.beomsu317.route.user.RegisterRoute
import org.koin.core.definition.Definition
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

val routeModules = listOf(module(createdAtStart = true) {
    registerRoute { RegisterRoute(get()) }
    registerRoute { LoginRoute(get()) }
    registerRoute { ProfileRoute(get(), get()) }
}
)

private inline fun <reified T : Route> Module.registerRoute(noinline definition: Definition<T>) =
    single(definition = definition) bind Route::class