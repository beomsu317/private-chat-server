package com.beomsu317.use_case.user

import com.beomsu317.use_case.exception.UserNotFoundException
import com.beomsu317.use_case.user.dto.FriendDto
import io.ktor.auth.jwt.*

class GetAllFriendsUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(principal: JWTPrincipal): GetAllFriendsResult {
        val id = principal.payload.getClaim("id").asString()
        val user = repository.getUserById(id) ?: throw UserNotFoundException()
        val allUsers = repository.getUsers()
        val users = (allUsers - user).map {
            FriendDto(
                id = it.id.toString(),
                email = it.email,
                photoUrl = it.photoUrl,
                displayName = it.displayName
            )
        }
        return GetAllFriendsResult(friends = users.toSet())
    }
}

@kotlinx.serialization.Serializable
data class GetAllFriendsResult(
    val friends: Set<FriendDto>
)