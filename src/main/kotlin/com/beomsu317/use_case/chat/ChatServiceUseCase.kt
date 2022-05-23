package com.beomsu317.use_case.chat

import com.beomsu317.use_case.UseCase
import com.beomsu317.use_case.exception.UnknownUserException
import com.beomsu317.use_case.exception.UserNotFoundException
import com.beomsu317.use_case.user.UserRepository
import io.ktor.auth.jwt.*
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ReceiveChannel

@UseCase
class ChatServiceUseCase(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(
        principal: JWTPrincipal,
        incoming: ReceiveChannel<Frame>,
        defaultWebSocketServerSession: DefaultWebSocketServerSession,
    ) {
        val id = principal.payload.getClaim("id").asString() ?: throw UnknownUserException()
        val user = userRepository.getUserById(id) ?: throw UserNotFoundException()

//        notificationController.addUserSession(id, defaultWebSocketServerSession)

        try {
            for (frame in incoming) {
                // for send notification
            }
        } catch (e: Throwable) {
            throw e
        } finally {
//            notificationController.removeUserSession(id)
        }
    }
}