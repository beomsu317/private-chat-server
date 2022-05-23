package com.beomsu317.use_case.chat

import com.beomsu317.use_case.chat.controller.UserSessionController
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
    private val roomController: UserSessionController
) {

    suspend operator fun invoke(principal: JWTPrincipal, request: JoinRoomRequest) {
        val id = principal.payload.getClaim("id").asString() ?: throw UnknownUserException()
        val room = chatRepository.getRoomById(request.roomId) ?: throw RoomNotFoundException()
        val user = userRepository.getUserById(id) ?: throw UserNotFoundException()
        val updatedRoom = room.copy(users = room.users + user.id)
        chatRepository.updateRoom(updatedRoom)

        val updatedUser = user.copy(rooms = user.rooms + ObjectId(request.roomId).toId())
        userRepository.updateUser(updatedUser)

//        roomController.sendMessage(request.roomId, "${user.displayName} joined this room")
    }
}

@kotlinx.serialization.Serializable
data class JoinRoomRequest(
    val roomId: String
)