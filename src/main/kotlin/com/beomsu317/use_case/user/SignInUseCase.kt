package com.beomsu317.use_case.user

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.beomsu317.entity.toDto
import com.beomsu317.use_case.common.validatePasswordHash
import com.beomsu317.use_case.exception.LoginFailedException
import com.beomsu317.use_case.user.dto.UserDto

class SignInUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(request: SignInRequest, secret: String): SignInResult {
        val user = repository.getUserByEmail(request.email) ?: throw LoginFailedException()
        if (!validatePasswordHash(user.passwordSha256WithSalt, request.password)) {
            throw LoginFailedException()
        }

        val token = JWT.create()
            .withClaim("id", user.id.toString())
            .withClaim("email", user.email)
            .withClaim("displayName", user.displayName)
            .withClaim("photoUrl", user.photoUrl)
            .sign(Algorithm.HMAC256(secret))
        return SignInResult(token, user.toDto())
    }
}

@kotlinx.serialization.Serializable
data class SignInRequest(
    val email: String,
    val password: String
)

@kotlinx.serialization.Serializable
data class SignInResult(
    val token: String,
    val user: UserDto
)