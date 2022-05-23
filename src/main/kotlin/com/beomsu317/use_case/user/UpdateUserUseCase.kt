package com.beomsu317.use_case.user

import com.beomsu317.entity.Room
import com.beomsu317.use_case.UseCase
import com.beomsu317.use_case.exception.EmptyDisplayNameException
import com.beomsu317.use_case.exception.UserNotFoundException
import com.beomsu317.use_case.user.dto.UserDto
import com.beomsu317.use_case.user.dto.toEntity
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

@UseCase
class UpdateUserUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(id: String, request: UpdateUserRequest) {
        val user = repository.getUserById(id) ?: throw UserNotFoundException()
        val updatedUser = user.copy(
            friends = request.userDto.friends.map { it.toEntity() }.toSet(),
            rooms = request.userDto.rooms.map { ObjectId(it).toId<Room>() }.toSet()
        )
        repository.updateUser(updatedUser)
    }
}

@kotlinx.serialization.Serializable
data class UpdateUserRequest(
    val userDto: UserDto
)