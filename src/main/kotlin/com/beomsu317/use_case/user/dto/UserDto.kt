package com.beomsu317.use_case.user.dto

import com.beomsu317.entity.UserFriend
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

@kotlinx.serialization.Serializable
data class UserDto(
    val id: String,
    val email: String,
    val displayName: String,
    val photoUrl: String,
    val friends: Set<UserFriendDto>,
    val rooms: Set<String>
)

@kotlinx.serialization.Serializable
data class UserFriendDto(
    val id: String,
    val priority: Int
)

fun UserFriendDto.toEntity(): UserFriend {
    return UserFriend(
        id = ObjectId(id).toId(),
        priority = priority
    )
}