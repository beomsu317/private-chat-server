package com.beomsu317.use_case.user

import com.beomsu317.use_case.UseCase
import com.beomsu317.use_case.exception.InappropriateFriendsIncludeException
import com.beomsu317.use_case.exception.UserNotFoundException
import com.beomsu317.use_case.user.dto.UserFriendDto
import com.beomsu317.use_case.user.dto.toEntity
import io.ktor.auth.jwt.*

@UseCase
class DeleteFriendsUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(principal: JWTPrincipal, request: DeleteFriendsRequest) {
        val id = principal.payload.getClaim("id").asString()
        val user = repository.getUserById(id) ?: throw UserNotFoundException()
        val friends = request.friends.map {
            repository.getUserById(it.id) ?: throw InappropriateFriendsIncludeException()
            if (user.id.toString() == it.id) {
                throw InappropriateFriendsIncludeException()
            }
            it.toEntity()
        }

        val updatedUser = user.copy(friends = user.friends - friends.toSet())
        repository.updateUser(updatedUser)
    }
}

@kotlinx.serialization.Serializable
data class DeleteFriendsRequest(
    val friends: Set<UserFriendDto>
)