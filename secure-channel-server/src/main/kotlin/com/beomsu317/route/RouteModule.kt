package com.beomsu317.route

import com.beomsu317.route.chat.chatRouteModule
import com.beomsu317.route.user.userRouteModule
import org.koin.core.definition.Definition
import org.koin.core.module.Module
import org.koin.dsl.bind

val routeModules = listOf(userRouteModule, chatRouteModule)

inline fun <reified T : Route> Module.registerRoute(noinline definition: Definition<T>) =
    single(definition = definition) bind Route::class