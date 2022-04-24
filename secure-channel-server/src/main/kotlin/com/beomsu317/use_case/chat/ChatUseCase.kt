package com.beomsu317.use_case.chat

import com.beomsu317.use_case.UseCase
import com.beomsu317.use_case.chat.dto.MessageDto
import com.beomsu317.use_case.chat.mapper.toEntity
import com.beomsu317.use_case.chat.repository.RoomRepository
import com.beomsu317.use_case.chat.repository.MessageRepository
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
    private val messageRepository: MessageRepository,
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

        roomController.addSession(roomId, defaultWebSocketServerSession)

        try {
            for (frame in incoming) {
                when (frame) {
                    is Frame.Text -> {
                        val receiveText = frame.readText()
                        val messageDto = Json.decodeFromString<MessageDto>(receiveText)
                        messageRepository.insertMessage(messageDto.toEntity())
                        roomController.sendMessage(roomId, Json.encodeToString(messageDto))
                    }
                }
            }
        } catch (e: Throwable) {
            throw e
        } finally {
            roomController.removeSession(roomId, defaultWebSocketServerSession)
        }
    }
}