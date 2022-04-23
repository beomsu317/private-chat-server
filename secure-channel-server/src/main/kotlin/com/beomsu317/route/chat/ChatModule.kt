package com.beomsu317.route.chat

import com.beomsu317.route.registerRoute
import org.koin.dsl.module

val chatRouteModule = module(createdAtStart = true) {
    registerRoute { ChatRoute(get()) }
}