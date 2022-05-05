package com.beomsu317.use_case.user

import com.beomsu317.entity.User
import com.beomsu317.use_case.user.dto.UserDto

fun User.toDto() = UserDto(
    id = id.toString(),
    email = email,
    displayName = displayName,
    photoUrl = photoUrl,
    friends = friends.map { it.toString() }.toSet(),
    rooms = rooms.map { it.toString() }.toSet()
)