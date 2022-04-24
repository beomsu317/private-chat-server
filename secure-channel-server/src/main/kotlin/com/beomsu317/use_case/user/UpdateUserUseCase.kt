package com.beomsu317.use_case.user

import com.beomsu317.use_case.UseCase
import com.beomsu317.use_case.exception.EmptyDisplayNameException
import com.beomsu317.use_case.exception.UserDoesNotFoundException
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

@UseCase
class UpdateUserUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(id: String, request: UpdateUserRequest) {
        val user = repository.getUserById(ObjectId(id).toId()) ?: throw UserDoesNotFoundException()
        if (request.displayName.isNullOrEmpty()) {
            throw EmptyDisplayNameException()
        }
        val updatedUser = user.copy(displayName = request.displayName, photoUrl = request.photoUrl)
        repository.updateUser(updatedUser)
    }
}

@kotlinx.serialization.Serializable
data class UpdateUserRequest(
    val displayName: String,
    val photoUrl: String
)