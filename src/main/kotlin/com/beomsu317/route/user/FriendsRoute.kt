package com.beomsu317.route.user

import com.beomsu317.route.Route
import com.beomsu317.use_case.exception.FriendIdNotSetException
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
    getFriendsUseCase: GetFriendsUseCase,
    getAllFriendsUseCase: GetAllFriendsUseCase,
    getFriendUseCase: GetFriendUseCase
) : Route({
    authenticate("jwt") {
        route("/user/friends") {
            get {
                val principal = call.principal<JWTPrincipal>() ?: throw UnknownUserException()
                val result = getFriendsUseCase(principal)
                call.respond(HttpStatusCode.OK, Response<GetFriendsResult>(result = result))
            }

            get("/{frinedId}") {
                val principal = call.principal<JWTPrincipal>() ?: throw UnknownUserException()
                val friendId = call.parameters["frinedId"] ?: throw FriendIdNotSetException()
                val result = getFriendUseCase(friendId)
                call.respond(HttpStatusCode.OK, Response<GetFriendResult>(result = result))
            }

            get("/all") {
                val principal = call.principal<JWTPrincipal>() ?: throw UnknownUserException()
                val result = getAllFriendsUseCase(principal)
                call.respond(HttpStatusCode.OK, Response<GetAllFriendsResult>(result = result))
            }

            post("/add") {
                val principal = call.principal<JWTPrincipal>() ?: throw UnknownUserException()
                val request = call.receive<AddFriendsRequest>()
                val result = addFriendsUseCase(principal, request)
                call.respond(HttpStatusCode.OK, Response<AddFriendResult>(result = result))
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