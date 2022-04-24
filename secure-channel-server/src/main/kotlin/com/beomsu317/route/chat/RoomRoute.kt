package com.beomsu317.route.chat

import com.beomsu317.entity.Room
import com.beomsu317.route.Route
import com.beomsu317.use_case.chat.*
import com.beomsu317.use_case.exception.UnknownUserException
import com.beomsu317.use_case.response.Response
import com.beomsu317.use_case.user.AddFriendsRequest
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

class RoomRoute(
    createRoomUseCase: CreateRoomUseCase,
    leaveRoomUseCase: LeaveRoomUseCase,
    joinRoomUseCase: JoinRoomUseCase
): Route({
    authenticate("jwt") {
        route("/chat/room") {

            post ("/create") {
                val principal = call.principal<JWTPrincipal>() ?: throw UnknownUserException()
                val request = call.receive<CreateRoomRequest>()
                val result = createRoomUseCase(principal, request)
                call.respond(HttpStatusCode.OK, Response<CreateRoomResult>(result = result))
            }

            post("/leave") {
                val principal = call.principal<JWTPrincipal>() ?: throw UnknownUserException()
                val request = call.receive<LeaveRoomRequest>()
                leaveRoomUseCase(principal, request)
                call.respond(HttpStatusCode.OK, Response<Unit>())
            }

            post("/join") {
                val principal = call.principal<JWTPrincipal>() ?: throw UnknownUserException()
                val request = call.receive<JoinRoomRequest>()
                joinRoomUseCase(principal, request)
                call.respond(HttpStatusCode.OK, Response<Unit>())
            }
        }
    }
})