package com.beomsu317.use_case.chat

import com.beomsu317.entity.Room
import com.beomsu317.use_case.chat.repository.RoomRepository
import com.beomsu317.use_case.exception.RoomNotFoundException
import com.beomsu317.use_case.exception.UnknownUserException
import com.beomsu317.use_case.exception.UserNotFoundException
import com.beomsu317.use_case.user.UserRepository
import io.ktor.auth.jwt.*
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

class JoinRoomUseCase(
    private val userRepository: UserRepository,
    private val chatRepository: RoomRepository,
    private val roomController: RoomController
) {

    suspend operator fun invoke(principal: JWTPrincipal, request: JoinRoomRequest) {
        val id = principal.payload.getClaim("id").asString() ?: throw UnknownUserException()
        val roomId = ObjectId(request.roomId).toId<Room>()
        val room = chatRepository.getRoomById(roomId) ?: throw RoomNotFoundException()
        val user = userRepository.getUserById(ObjectId(id).toId()) ?: throw UserNotFoundException()
        val updatedRoom = room.copy(users = room.users + user.id)
        chatRepository.updateRoom(updatedRoom)

        val updatedUser = user.copy(rooms = user.rooms + roomId)
        userRepository.updateUser(updatedUser)

        roomController.sendMessage(roomId.toString(), "${user.displayName} joins ${room.title}")
    }
}

@kotlinx.serialization.Serializable
data class JoinRoomRequest(
    val roomId: String
)