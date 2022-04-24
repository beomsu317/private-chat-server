package com.beomsu317.use_case.chat

import com.beomsu317.use_case.UseCase
import com.beomsu317.use_case.exception.UnknownUserException
import com.beomsu317.use_case.exception.UserDoesNotFoundException
import com.beomsu317.use_case.user.UserRepository
import io.ktor.auth.jwt.*
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@UseCase
class ChatUseCase(
    private val roomController: RoomController
) {
    suspend operator fun invoke(
        principal: JWTPrincipal,
        roomId: String,
        incoming: ReceiveChannel<Frame>,
        defaultWebSocketServerSession: DefaultWebSocketServerSession
    ) {
        val displayName = principal.payload.getClaim("displayName").asString()
        if (displayName.isNullOrEmpty()) {
            throw UserDoesNotFoundException()
        }

        roomController.addSession(roomId, defaultWebSocketServerSession)
        val sessions = roomController.getSessions(roomId)
        sessions.forEach {
            it.send("${displayName} joins this room")
        }

        try {
            for (frame in incoming) {
                when (frame) {
                    is Frame.Text -> {
                        val receiveText = frame.readText()
                        val message = Json.decodeFromString<MessageDto>(receiveText)
                        if (message.displayName != displayName) {
                            throw UnknownUserException()
                        }
                        sessions.forEach {
                            it.send(Json.encodeToString(message))
                        }
                    }
                }
            }
        } catch (e: Throwable) {
            throw e
        } finally {
            roomController.removeSession(roomId, defaultWebSocketServerSession)
            sessions.forEach {
                it.send("${displayName} left this room")
            }
        }
    }
}