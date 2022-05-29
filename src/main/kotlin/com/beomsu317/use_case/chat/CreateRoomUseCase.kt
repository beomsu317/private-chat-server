package com.beomsu317.use_case.chat

import com.beomsu317.entity.Room
import com.beomsu317.entity.User
import com.beomsu317.use_case.chat.dto.RoomDto
import com.beomsu317.use_case.chat.repository.RoomRepository
import com.beomsu317.use_case.exception.RoomNotFoundException
import com.beomsu317.use_case.exception.UnknownUserException
import com.beomsu317.use_case.exception.UserNotFoundException
import com.beomsu317.use_case.user.UserRepository
import io.ktor.auth.jwt.*
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

class CreateRoomUseCase(
    private val userRepository: UserRepository,
    private val chatRepository: RoomRepository,
) {

    suspend operator fun invoke(principal: JWTPrincipal, request: CreateRoomRequest): CreateRoomResult {
        val id = principal.payload.getClaim("id").asString() ?: throw UnknownUserException()
        val owner = userRepository.getUserById(id) ?: throw UserNotFoundException()

        owner.rooms.forEach { roomId ->
            val room = chatRepository.getRoomById(roomId.toString()) ?: throw RoomNotFoundException()
            val users = room.users - owner.id
            if (users.contains(ObjectId(request.userId).toId())) {
                return CreateRoomResult(room.toDto())
            }
        }

        val room = Room(
            owner = owner.id,
            users = setOf(ObjectId(request.userId).toId<User>(), owner.id)
        )
        room.users.forEach {
            val user = userRepository.getUserById(it.toString()) ?: return@forEach
            val updatedUser = user.copy(rooms = user.rooms + room.id)
            userRepository.updateUser(updatedUser)
        }
        chatRepository.insertRoom(room)

        return CreateRoomResult(room.toDto())
    }
}

@kotlinx.serialization.Serializable
data class CreateRoomRequest(
    val userId: String
)

@kotlinx.serialization.Serializable
data class CreateRoomResult(
    val room: RoomDto
)