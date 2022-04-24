package com.beomsu317.route.user

import com.beomsu317.route.Route
import com.beomsu317.use_case.exception.ConfigurationNotFoundException
import com.beomsu317.use_case.response.Response
import com.beomsu317.use_case.user.LoginRequest
import com.beomsu317.use_case.user.LoginResult
import com.beomsu317.use_case.user.LoginUseCase
import com.beomsu317.use_case.user.UploadImageResult
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

class LoginRoute(
    loginUseCase: LoginUseCase
): Route({
    route("/user/login") {
        post {
            val request = call.receive<LoginRequest>()
            val jwtSecret = application.environment.config.propertyOrNull("jwt.secret") ?: throw ConfigurationNotFoundException()
            val result = loginUseCase(request, jwtSecret.getString())
            call.respond(HttpStatusCode.OK, Response<LoginResult>(result = result))
        }
    }
})