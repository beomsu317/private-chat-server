package com.beomsu317.use_case.user

import com.beomsu317.entity.User
import com.beomsu317.use_case.UseCase
import com.beomsu317.use_case.common.checkPasswordConstraints
import com.beomsu317.use_case.common.getSha256WithSalt
import com.beomsu317.use_case.exception.*

@UseCase
class RegisterUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(request: RegisterUserRequest) {
        if (!Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$").containsMatchIn(request.email)) {
            throw NotEmailAddressException()
        }
        if (request.password.isNullOrEmpty() || request.confirmPassword.isNullOrEmpty()) {
            throw PasswordEmptyException()
        }
        if (!checkPasswordConstraints(request.password)) {
            throw PasswordConstraintsException()
        }
        if (request.password != request.confirmPassword) {
            throw ConfirmPasswordNotMatchException()
        }
        if (repository.getUserByEmail(request.email) != null) {
            throw EmailConflictException()
        }
        val user = request.toEntity()
        repository.insertUser(user)
    }

    private fun RegisterUserRequest.toEntity() = User(
        email = email,
        passwordSha256WithSalt = getSha256WithSalt(password),
        displayName = "",
        photoUrl = "",
        friends = emptyList(),
        rooms = emptyList()
    )
}

@kotlinx.serialization.Serializable
data class RegisterUserRequest(
    val email: String,
    val password: String,
    val confirmPassword: String
)