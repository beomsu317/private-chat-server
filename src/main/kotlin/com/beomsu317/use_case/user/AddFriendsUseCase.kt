package com.beomsu317.use_case.user

import com.beomsu317.use_case.exception.FriendConflictException
import com.beomsu317.use_case.exception.InappropriateFriendsIncludeException
import com.beomsu317.use_case.exception.UserNotFoundException
import com.beomsu317.use_case.user.dto.FriendDto
import com.beomsu317.use_case.user.dto.UserFriendDto
import com.beomsu317.use_case.user.dto.toEntity
import io.ktor.auth.jwt.*

class AddFriendsUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(principal: JWTPrincipal, request: AddFriendsRequest): AddFriendResult {
        val id = principal.payload.getClaim("id").asString()
        val user = repository.getUserById(id) ?: throw UserNotFoundException()
        var friends = emptySet<FriendDto>()
        val userFriends = request.friends.map {
            val friend = repository.getUserById(it.id) ?: throw InappropriateFriendsIncludeException()
            friends += FriendDto(
                id = friend.id.toString(),
                email = friend.email,
                photoUrl = friend.photoUrl,
                displayName = friend.displayName,
                numberOfFriends = friend.friends.size,
                numberOfRooms = friend.rooms.size
            )
            if (user.id.toString() == it.id) {
                throw InappropriateFriendsIncludeException()
            }
            val friendIds = user.friends.map { it.id.toString() }
            if (friendIds.contains(it.id)) {
                throw FriendConflictException()
            }
            it.toEntity()
        }

        val updatedUser = user.copy(friends = user.friends + userFriends.toSet())
        repository.updateUser(updatedUser)
        return AddFriendResult(friends)
    }
}

@kotlinx.serialization.Serializable
data class AddFriendsRequest(
    val friends: Set<UserFriendDto>
)

@kotlinx.serialization.Serializable
data class AddFriendResult(
    val friends: Set<FriendDto>
)