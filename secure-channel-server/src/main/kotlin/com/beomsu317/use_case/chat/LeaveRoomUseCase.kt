package com.beomsu317.use_case.chat

import com.beomsu317.entity.Room
import com.beomsu317.use_case.exception.RoomDoesNotFoundException
import com.beomsu317.use_case.exception.UserDoesNotFoundException
import com.beomsu317.use_case.user.UserRepository
import io.ktor.auth.jwt.*
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

class LeaveRoomUseCase(
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository
) {

    suspend operator fun invoke(
        principal: JWTPrincipal,
        request: LeaveRoomRequest
    ) {
        val id = principal.payload.getClaim("id").asString()
        val user = userRepository.getUserById(ObjectId(id).toId()) ?: throw UserDoesNotFoundException()
        val roomId = ObjectId(request.roomId).toId<Room>()
        val updatedUser = user.copy(rooms = user.rooms - roomId)
        userRepository.updateUser(updatedUser)

        val room = chatRepository.getRoomById(roomId) ?: throw RoomDoesNotFoundException()
        val updatedRoom = room.copy(users = room.users - user.id)
        if (updatedRoom.users.isEmpty()) {
            chatRepository.deleteRoom(room)
        } else {
            chatRepository.updateRoom(updatedRoom)
        }
    }
}

@kotlinx.serialization.Serializable
data class LeaveRoomRequest(
    val roomId: String
)