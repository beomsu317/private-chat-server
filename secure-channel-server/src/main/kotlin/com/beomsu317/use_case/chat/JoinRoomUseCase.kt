package com.beomsu317.use_case.chat

import com.beomsu317.entity.Room
import com.beomsu317.use_case.exception.RoomDoesNotFoundException
import com.beomsu317.use_case.exception.UnknownUserException
import com.beomsu317.use_case.exception.UserDoesNotFoundException
import com.beomsu317.use_case.user.UserRepository
import io.ktor.auth.jwt.*
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

class JoinRoomUseCase(
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository
) {

    suspend operator fun invoke(principal: JWTPrincipal, request: JoinRoomRequest) {
        val id = principal.payload.getClaim("id").asString() ?: throw UnknownUserException()
        val roomId = ObjectId(request.roomId).toId<Room>()
        val room = chatRepository.getRoomById(roomId) ?: throw RoomDoesNotFoundException()
        val user = userRepository.getUserById(ObjectId(id).toId()) ?: throw UserDoesNotFoundException()
        val updatedRoom = room.copy(users = room.users + user.id)
        chatRepository.updateRoom(updatedRoom)

        val updatedUser = user.copy(rooms = user.rooms + roomId)
        userRepository.updateUser(updatedUser)
    }
}

@kotlinx.serialization.Serializable
data class JoinRoomRequest(
    val roomId: String
)