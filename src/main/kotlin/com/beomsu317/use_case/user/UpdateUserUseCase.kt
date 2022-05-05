package com.beomsu317.use_case.user

import com.beomsu317.use_case.UseCase
import com.beomsu317.use_case.exception.EmptyDisplayNameException
import com.beomsu317.use_case.exception.UserNotFoundException
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

@UseCase
class UpdateUserUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(id: String, request: UpdateUserRequest) {
        val user = repository.getUserById(id) ?: throw UserNotFoundException()
        val updatedUser = user.copy(photoUrl = request.photoUrl)
        repository.updateUser(updatedUser)
    }
}

@kotlinx.serialization.Serializable
data class UpdateUserRequest(
    val photoUrl: String
)