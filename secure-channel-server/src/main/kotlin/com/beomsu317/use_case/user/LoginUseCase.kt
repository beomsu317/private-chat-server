package com.beomsu317.use_case.user

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.beomsu317.use_case.common.validatePasswordHash
import com.beomsu317.use_case.exception.LoginFailedException

class LoginUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(request: LoginRequest, secret: String): LoginResult {
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
        return LoginResult(token)
    }
}

@kotlinx.serialization.Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@kotlinx.serialization.Serializable
data class LoginResult(
    val token: String
)