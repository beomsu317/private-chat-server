package com.beomsu317.use_case.user

import com.beomsu317.use_case.exception.UserNotFoundException
import com.beomsu317.use_case.user.dto.FriendDto
import io.ktor.auth.jwt.*

class GetSearchFriendsUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(principal: JWTPrincipal, request: GetSearchFriendsRequest): GetSearchFriendsResult {
        val id = principal.payload.getClaim("id").asString()
        val user = repository.getUserById(id) ?: throw UserNotFoundException()
        val friendIds = user.friends.map { it.id }
        val allUsers = repository.getUsers(request.searchText)
        val users = (allUsers - user)
            .filter {
                !friendIds.contains(it.id)
            }
            .map {
                FriendDto(
                    id = it.id.toString(),
                    email = it.email,
                    photoUrl = it.photoUrl,
                    displayName = it.displayName,
                    numberOfFriends = it.friends.size + 1,
                    numberOfRooms = it.rooms.size + 1
                )
            }
        return GetSearchFriendsResult(friends = users.toSet())
    }
}

@kotlinx.serialization.Serializable
data class GetSearchFriendsRequest(
    val searchText: String
)

@kotlinx.serialization.Serializable
data class GetSearchFriendsResult(
    val friends: Set<FriendDto>
)