package com.beomsu317.use_case.user

@kotlinx.serialization.Serializable
data class UserDto(
    val email: String,
    val displayName: String,
    val photoUrl: String
)