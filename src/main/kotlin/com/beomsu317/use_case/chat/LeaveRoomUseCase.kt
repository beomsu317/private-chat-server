package com.beomsu317.use_case.chat

import com.beomsu317.entity.Room
import com.beomsu317.use_case.chat.controller.RoomController
import com.beomsu317.use_case.chat.repository.RoomRepository
import com.beomsu317.use_case.exception.RoomNotFoundException
import com.beomsu317.use_case.exception.UserNotFoundException
import com.beomsu317.use_case.user.UserRepository
import io.ktor.auth.jwt.*
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
        val user = userRepository.getUserById(id) ?: throw UserNotFoundException()
        val updatedUser = user.copy(rooms = user.rooms - ObjectId(request.roomId).toId<Room>())
        userRepository.updateUser(updatedUser)

        val room = chatRepository.getRoomById(request.roomId) ?: throw RoomNotFoundException()
        val updatedRoom = room.copy(users = room.users - user.id)
        if (updatedRoom.users.isEmpty()) {
            chatRepository.deleteRoom(room)
        } else {
            chatRepository.updateRoom(updatedRoom)
            roomController.sendMessage(request.roomId, "${user.displayName} left ${room.title}")
        }
    }
}

@kotlinx.serialization.Serializable
data class LeaveRoomRequest(
    val roomId: String
)