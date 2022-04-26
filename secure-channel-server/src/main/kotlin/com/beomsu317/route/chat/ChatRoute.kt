package com.beomsu317.route.chat

import com.beomsu317.route.Route
import com.beomsu317.use_case.chat.ChatUseCase
import com.beomsu317.use_case.exception.ParameterNotFoundException
import com.beomsu317.use_case.exception.UnknownUserException
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*

class ChatRoute(
    private val chatUseCase: ChatUseCase
) : Route({
    authenticate("jwt") {
        route("/chat") {
            webSocket("/{roomId}") {
                val roomId = call.parameters["roomId"] ?: throw ParameterNotFoundException()
                val principal = call.principal<JWTPrincipal>() ?: throw UnknownUserException()
                chatUseCase.invoke(principal, roomId, incoming, this)
            }
        }
    }

    webSocket("/c") {
        try {
            for (frame in incoming) {
                when (frame) {
                    is Frame.Text -> {
                        val receiveText = frame.readText()
                        application.log.info(receiveText)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
})
