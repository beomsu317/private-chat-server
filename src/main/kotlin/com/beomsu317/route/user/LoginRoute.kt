package com.beomsu317.route.user

import com.beomsu317.route.Route
import com.beomsu317.use_case.exception.ConfigurationNotFoundException
import com.beomsu317.use_case.response.Response
import com.beomsu317.use_case.user.SignInRequest
import com.beomsu317.use_case.user.SignInResult
import com.beomsu317.use_case.user.SignInUseCase
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

class LoginRoute(
    loginUseCase: SignInUseCase
): Route({
    route("/user/login") {
        post {
            val request = call.receive<SignInRequest>()
            val jwtSecret = application.environment.config.propertyOrNull("jwt.secret") ?: throw ConfigurationNotFoundException()
            val result = loginUseCase(request, jwtSecret.getString())
            call.respond(HttpStatusCode.OK, Response<SignInResult>(result = result))
        }
    }
})