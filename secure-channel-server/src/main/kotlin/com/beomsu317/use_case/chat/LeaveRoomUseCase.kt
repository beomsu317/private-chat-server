package com.beomsu317.use_case.chat

import com.beomsu317.entity.Room
import com.beomsu317.use_case.chat.repository.RoomRepository
import com.beomsu317.use_case.exception.RoomNotFoundException
import com.beomsu317.use_case.exception.UserNotFoundException
import com.beomsu317.use_case.user.UserRepository
import io.ktor.auth.jwt.*
import io.ktor.http.cio.websocket.*
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

class LeaveRoomUseCase(
    private val userRepository: UserRepository,
    private val chatRepository: RoomRepository,
    private val roomController: RoomController
) {

    suspend operator fun invoke(
        principal: JWTPrincipal,
        request: LeaveRoomRequest
    ) {
        val id = principal.payload.getClaim("id").asString()
        val user = userRepository.getUserById(ObjectId(id).toId()) ?: throw UserNotFoundException()
        val roomId = ObjectId(request.roomId).toId<Room>()
        val updatedUser = user.copy(rooms = user.rooms - roomId)
        userRepository.updateUser(updatedUser)

        val room = chatRepository.getRoomById(roomId) ?: throw RoomNotFoundException()
        val updatedRoom = room.copy(users = room.users - user.id)
        if (updatedRoom.users.isEmpty()) {
            chatRepository.deleteRoom(room)
        } else {
            chatRepository.updateRoom(updatedRoom)
            roomController.sendMessage(roomId.toString(), "${user.displayName} left ${room.title}")
        }
    }
}

@kotlinx.serialization.Serializable
data class LeaveRoomRequest(
    val roomId: String
)