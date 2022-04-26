package com.beomsu317.use_case.chat

import com.beomsu317.use_case.UseCase
import com.beomsu317.use_case.chat.dto.MessageDto
import com.beomsu317.use_case.chat.repository.RoomRepository
import com.beomsu317.use_case.exception.NoPermissionForRoomException
import com.beomsu317.use_case.exception.RoomNotFoundException
import com.beomsu317.use_case.exception.UnknownUserException
import com.beomsu317.use_case.exception.UserNotFoundException
import com.beomsu317.use_case.user.UserRepository
import io.ktor.auth.jwt.*
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

@UseCase
class ChatUseCase(
    private val userRepository: UserRepository,
    private val chatRepository: RoomRepository,
    private val roomController: RoomController
) {
    suspend operator fun invoke(
        principal: JWTPrincipal,
        roomId: String,
        incoming: ReceiveChannel<Frame>,
        defaultWebSocketServerSession: DefaultWebSocketServerSession
    ) {
        val id = principal.payload.getClaim("id").asString() ?: throw UnknownUserException()
        val user = userRepository.getUserById(ObjectId(id).toId()) ?: throw UserNotFoundException()
        val room = chatRepository.getRoomById(ObjectId(roomId).toId()) ?: throw RoomNotFoundException()

        roomController.addSession(room.id.toString(), defaultWebSocketServerSession)

        if (!room.users.contains(user.id)) {
            throw NoPermissionForRoomException()
        }

        try {
            for (frame in incoming) {
                when (frame) {
                    is Frame.Text -> {
                        val receiveText = frame.readText()
                        val messageDto = Json.decodeFromString<MessageDto>(receiveText)
                        if (messageDto.displayName != user.displayName) {
                            throw UnknownUserException()
                        }
                        roomController.sendMessage(room.id.toString(), Json.encodeToString(messageDto))
                    }
                }
            }
        } catch (e: Throwable) {
            throw e
        } finally {
            roomController.removeSession(room.id.toString(), defaultWebSocketServerSession)
        }
    }
}