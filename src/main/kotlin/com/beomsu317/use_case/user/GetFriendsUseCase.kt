package com.beomsu317.use_case.user

import com.beomsu317.use_case.exception.FriendNotFoundException
import com.beomsu317.use_case.exception.UserNotFoundException
import com.beomsu317.use_case.user.dto.FriendDto
import io.ktor.auth.jwt.*
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

class GetFriendsUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(principal: JWTPrincipal): GetFriendsResult {
        val id = principal.payload.getClaim("id").asString()
        val user = repository.getUserById(id) ?: throw UserNotFoundException()
        val friends = user.friends
            .map {
                val friend = repository.getUserById(it.toString()) ?: throw FriendNotFoundException()
                FriendDto(
                    id = friend.id.toString(),
                    email = friend.email,
                    photoUrl = friend.photoUrl,
                    displayName = friend.displayName
                )
            }.toSet()
        return GetFriendsResult(friends)
    }
}

@kotlinx.serialization.Serializable
data class GetFriendsResult(
    val friends: Set<FriendDto>
)
