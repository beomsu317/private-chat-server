package com.beomsu317.route.chat

import com.beomsu317.route.Route
import com.beomsu317.use_case.chat.ChatServiceUseCase
import com.beomsu317.use_case.chat.ChatUseCase
import com.beomsu317.use_case.exception.ParameterNotFoundException
import com.beomsu317.use_case.exception.UnknownUserException
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.routing.*
import io.ktor.websocket.*

class ChatRoute(
    private val chatUseCase: ChatUseCase,
    private val chatServiceUseCase: ChatServiceUseCase
) : Route({
    authenticate("jwt") {
        route("/chat") {

            webSocket("/service") {
                val principal = call.principal<JWTPrincipal>() ?: throw UnknownUserException()
                chatServiceUseCase.invoke(principal, incoming, this)
            }

            webSocket("/chat-server") {
                val principal = call.principal<JWTPrincipal>() ?: throw UnknownUserException()
                chatUseCase.invoke(principal, incoming, this)
            }
        }
    }
})
