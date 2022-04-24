package com.beomsu317.route.user

import com.beomsu317.route.Route
import com.beomsu317.use_case.exception.UnknownUserException
import com.beomsu317.use_case.user.AddFriendsRequest
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.request.*
import io.ktor.routing.*

class FriendsRoute() : Route({
    authenticate("jwt") {
        route("/user/friends") {
            get {

            }

            post("/add") {
                val principal = call.principal<JWTPrincipal>() ?: throw UnknownUserException()
                val email = principal.payload.getClaim("email").asString()
                val request = call.receive<AddFriendsRequest>()

            }
        }
    }
})