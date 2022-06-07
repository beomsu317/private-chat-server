package com.beomsu317.use_case.user

import com.beomsu317.use_case.UseCase
import com.beomsu317.use_case.exception.UnknownUserException
import com.beomsu317.use_case.user.dto.FriendDto
import io.ktor.auth.jwt.*

@UseCase
class GetFriendUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(friendId: String): GetFriendResult {
        val friend = repository.getUserById(friendId) ?: throw UnknownUserException()
        return GetFriendResult(
            friend = FriendDto(
                id = friend.id.toString(),
                email = friend.email,
                photoUrl = friend.photoUrl,
                displayName = friend.displayName,
                numberOfFriends = friend.friends.size,
                numberOfRooms = friend.rooms.size
            )
        )
    }
}

@kotlinx.serialization.Serializable
data class GetFriendResult(
    val friend: FriendDto
)