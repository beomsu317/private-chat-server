package com.beomsu317.use_case.user

@kotlinx.serialization.Serializable
data class UserDto(
    val id: String,
    val email: String,
    val displayName: String,
    val photoUrl: String,
    val friends: Set<String>,
    val rooms: Set<String>
)