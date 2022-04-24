package com.beomsu317.use_case.chat

import com.beomsu317.entity.Room
import com.beomsu317.entity.User
import com.beomsu317.use_case.exception.UnknownUserException
import com.beomsu317.use_case.exception.UserDoesNotFoundException
import com.beomsu317.use_case.user.UserRepository
import io.ktor.auth.jwt.*
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

class CreateRoomUseCase(
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository
) {

    suspend operator fun invoke(principal: JWTPrincipal, request: CreateRoomRequest): CreateRoomResult {
        val id = principal.payload.getClaim("id").asString() ?: throw UnknownUserException()
        val owner = userRepository.getUserById(ObjectId(id).toId()) ?: throw UserDoesNotFoundException()
        val room = Room(
            title = request.title,
            owner = owner.id,
            users = request.users.map { ObjectId(it).toId<User>() }.toSet() + owner.id
        )
        chatRepository.insertRoom(room)
        room.users.forEach {
            val user = userRepository.getUserById(it) ?: throw UserDoesNotFoundException()
            val updatedUser = user.copy(rooms = user.rooms + room.id)
            userRepository.updateUser(updatedUser)
        }
        return CreateRoomResult(room.toDto())
    }
}

@kotlinx.serialization.Serializable
data class CreateRoomRequest(
    val title: String,
    val users: Set<String>,
)

@kotlinx.serialization.Serializable
data class CreateRoomResult(
    val room: RoomDto
)