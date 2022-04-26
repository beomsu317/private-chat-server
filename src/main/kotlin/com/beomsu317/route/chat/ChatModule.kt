package com.beomsu317.route.chat

import com.beomsu317.route.registerRoute
import org.koin.dsl.module

val chatRouteModules = module(createdAtStart = true) {
    registerRoute { ChatRoute(get(), get()) }
    registerRoute { RoomRoute(get(), get(), get()) }
}