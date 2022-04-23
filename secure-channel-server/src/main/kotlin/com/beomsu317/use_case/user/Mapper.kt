package com.beomsu317.use_case.user

import com.beomsu317.entity.User

fun User.toDto() = UserDto(
    email = email,
    displayName = displayName,
    photoUrl = photoUrl
)