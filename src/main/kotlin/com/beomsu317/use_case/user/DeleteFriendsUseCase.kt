package com.beomsu317.use_case.user

import com.beomsu317.entity.User
import com.beomsu317.use_case.UseCase
import com.beomsu317.use_case.exception.InappropriateFriendsIncludeException
import com.beomsu317.use_case.exception.UserNotFoundException
import io.ktor.auth.jwt.*
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

@UseCase
class DeleteFriendsUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(principal: JWTPrincipal, request: DeleteFriendsRequest) {
        val id = principal.payload.getClaim("id").asString()
        val user = repository.getUserById(id) ?: throw UserNotFoundException()
        val friendIds = request.friends.map {
            val friend = repository.getUserById(it) ?: throw InappropriateFriendsIncludeException()
            if (user.id.toString() == it) {
                throw InappropriateFriendsIncludeException()
            }
            friend.id
        }

        val updatedUser = user.copy(friends = user.friends - friendIds.toSet())
        repository.updateUser(updatedUser)
    }
}

@kotlinx.serialization.Serializable
data class DeleteFriendsRequest(
    val friends: Set<String>
)