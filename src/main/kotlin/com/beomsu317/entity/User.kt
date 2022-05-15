package com.beomsu317.entity

import com.beomsu317.use_case.user.dto.UserDto
import com.beomsu317.use_case.user.dto.UserFriendDto
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.id.StringId
import org.litote.kmongo.newId

data class User(
    @BsonId
    val id: Id<User> = newId(),
    val email: String,
    val passwordSha256WithSalt: String,
    val displayName: String,
    val photoUrl: String,
    val friends: Set<UserFriend>,
    val rooms: Set<Id<Room>>
)

data class UserFriend(
    val id: Id<User>,
    val priority: Int
)

fun User.toDto() = UserDto(
    id = id.toString(),
    email = email,
    displayName = displayName,
    photoUrl = photoUrl,
    friends = friends.map { it.toDto() }.toSet(),
    rooms = rooms.map { it.toString() }.toSet()
)

fun UserFriend.toDto(): UserFriendDto {
    return UserFriendDto(
        id = id.toString(),
        priority = priority
    )
}