package com.beomsu317.plugins

import com.beomsu317.use_case.exception.*
import com.beomsu317.use_case.exception.NotFoundException
import com.beomsu317.use_case.response.Response
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.configurateStatusPages() {
    install(StatusPages) {
        exception<BaseException> { e ->
            call.respond(
                e.statusCode,
                Response<Unit>(
                    code = e.code.toString(), message = e.message.toString()
                )
            )
        }

        exception<Throwable> { e ->
            e.printStackTrace()
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}