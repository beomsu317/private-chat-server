package com.beomsu317.route.user

import com.beomsu317.route.Route
import com.beomsu317.use_case.exception.ConfigurationNotFoundException
import com.beomsu317.use_case.exception.UnknownUserException
import com.beomsu317.use_case.response.Response
import com.beomsu317.use_case.user.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.nio.file.Paths

class ProfileRoute(
    private val uploadImageUseCase: UploadImageUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : Route({
    authenticate("jwt") {
        route("/user/profile") {
            get {
                val principal = call.principal<JWTPrincipal>() ?: throw UnknownUserException()
                val email = principal.payload.getClaim("email").asString()
                val result = getUserUseCase(email)
                call.respond(HttpStatusCode.OK, Response<GetUserResult>(result = result))
            }

            post("/update") {
                val principal = call.principal<JWTPrincipal>() ?: throw UnknownUserException()
                val email = principal.payload.getClaim("email").asString()
                val request = call.receive<UpdateUserRequest>()
                updateUserUseCase(email, request)
                call.respond(HttpStatusCode.OK, Response<Unit>())
            }

            post("/upload-image") {
                val principle = call.principal<JWTPrincipal>() ?: throw UnknownUserException()
                val email = principle.payload.getClaim("email").asString()
                val multipart = call.receiveMultipart()
                val host = application.environment.config.propertyOrNull("ktor.deployment.host") ?: throw ConfigurationNotFoundException()
                val port = application.environment.config.propertyOrNull("ktor.deployment.port") ?: throw ConfigurationNotFoundException()
                val url = "http://${host.getString()}:${port.getString()}/"
                lateinit var result: UploadImageResult
                multipart.forEachPart { part ->
                    result = uploadImageUseCase(email, part, url)
                }
                call.respond(HttpStatusCode.OK, Response<UploadImageResult>(result = result))
            }
        }
    }
})

