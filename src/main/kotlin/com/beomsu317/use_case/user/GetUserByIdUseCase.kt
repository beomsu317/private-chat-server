package com.beomsu317.use_case.user

import com.beomsu317.use_case.exception.UnknownUserException
import com.beomsu317.use_case.user.dto.UserDto
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

class GetUserByIdUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(id: String): GetUserByIdResult {
        val user = repository.getUserById(id) ?: throw UnknownUserException()
        return GetUserByIdResult(user.toDto())
    }
}

@kotlinx.serialization.Serializable
data class GetUserByIdResult(
    val user: UserDto
)