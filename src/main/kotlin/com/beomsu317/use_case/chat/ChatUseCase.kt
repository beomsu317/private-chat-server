package com.beomsu317.use_case.chat

import com.beomsu317.use_case.UseCase
import com.beomsu317.use_case.chat.controller.UserSessionController
import com.beomsu317.use_case.chat.dto.MessageDto
import com.beomsu317.use_case.chat.repository.RoomRepository
import com.beomsu317.use_case.exception.RoomNotFoundException
import com.beomsu317.use_case.exception.UnknownUserException
import com.beomsu317.use_case.exception.UserNotFoundException
import com.beomsu317.use_case.user.UserRepository
import com.fasterxml.jackson.core.JsonParseException
import io.ktor.auth.jwt.*
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@UseCase
class ChatUseCase(
    private val userRepository: UserRepository,
    private val chatRepository: RoomRepository,
    private val sessionController: UserSessionController
) {
    suspend operator fun invoke(
        principal: JWTPrincipal,
        incoming: ReceiveChannel<Frame>,
        defaultWebSocketServerSession: DefaultWebSocketServerSession
    ) {
        val id = principal.payload.getClaim("id").asString() ?: throw UnknownUserException()
        val user = userRepository.getUserById(id) ?: throw UserNotFoundException()

        sessionController.addSession(user.id.toString(), defaultWebSocketServerSession)

        try {
            for (frame in incoming) {
                when (frame) {
                    is Frame.Text -> {
                        try {
                            val receiveText = frame.readText()
                            val messageDto = Json.decodeFromString<MessageDto>(receiveText)
                            if (messageDto.senderId != user.id.toString()) {
                                throw UnknownUserException()
                            }
                            val room = chatRepository.getRoomById(messageDto.roomId) ?: throw RoomNotFoundException()
                            room.users.forEach { id ->
                                sessionController.sendMessage(id.toString(), Json.encodeToString(messageDto.copy(displayName = user.displayName)))
                            }
                        } catch (e: SerializationException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        } finally {
            sessionController.removeSession(user.id.toString())
        }
    }
}