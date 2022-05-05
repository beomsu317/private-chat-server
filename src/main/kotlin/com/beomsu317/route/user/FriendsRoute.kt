package com.beomsu317.route.user

import com.beomsu317.route.Route
import com.beomsu317.use_case.exception.UnknownUserException
import com.beomsu317.use_case.response.Response
import com.beomsu317.use_case.user.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

class FriendsRoute(
    addFriendsUseCase: AddFriendsUseCase,
    deleteFriendsUseCase: DeleteFriendsUseCase,
    getFriendsUseCase: GetFriendsUseCase
) : Route({
    authenticate("jwt") {
        route("/user/friends") {
            get {
                val principal = call.principal<JWTPrincipal>() ?: throw UnknownUserException()
                val result = getFriendsUseCase(principal)
                call.respond(HttpStatusCode.OK, Response<GetFriendsResult>(result = result))
            }

            post("/add") {
                val principal = call.principal<JWTPrincipal>() ?: throw UnknownUserException()
                val request = call.receive<AddFriendsRequest>()
                addFriendsUseCase(principal, request)
                call.respond(HttpStatusCode.OK, Response<Unit>())
            }

            post("/delete") {
                val principal = call.principal<JWTPrincipal>() ?: throw UnknownUserException()
                val request = call.receive<DeleteFriendsRequest>()
                deleteFriendsUseCase(principal, request)
                call.respond(HttpStatusCode.OK, Response<Unit>())
            }
        }
    }
})