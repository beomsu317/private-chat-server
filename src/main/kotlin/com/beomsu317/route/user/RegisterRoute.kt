package com.beomsu317.route.user

import com.beomsu317.route.Route
import com.beomsu317.use_case.response.Response
import com.beomsu317.use_case.user.RegisterUserRequest
import com.beomsu317.use_case.user.RegisterUserUseCase
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

class RegisterRoute(
    private val registerUserUseCase: RegisterUserUseCase
) : Route({
    route("/user/register") {
        post {
            val request = call.receive<RegisterUserRequest>()
            registerUserUseCase(request)
            call.respond(HttpStatusCode.Created, Response<Unit>())
        }
    }
})