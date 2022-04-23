package com.beomsu317.use_case.user

import com.beomsu317.use_case.exception.UnknownUserException

class GetUserUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(email: String): GetUserResult {
        val user = repository.getUserByEmail(email) ?: throw UnknownUserException()
        return GetUserResult(user.toDto())
    }
}

@kotlinx.serialization.Serializable
data class GetUserResult(
    val user: UserDto
)