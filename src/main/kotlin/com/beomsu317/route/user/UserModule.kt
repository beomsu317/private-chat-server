package com.beomsu317.route.user

import com.beomsu317.route.registerRoute
import org.koin.dsl.module

val userRouteModule = module(createdAtStart = true) {
    registerRoute { RegisterRoute(get()) }
    registerRoute { LoginRoute(get()) }
    registerRoute { ProfileRoute(get(), get(), get()) }
    registerRoute { FriendsRoute(get(), get(), get(), get()) }
}