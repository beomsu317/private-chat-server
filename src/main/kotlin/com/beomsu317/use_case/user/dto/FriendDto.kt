package com.beomsu317.use_case.user.dto

@kotlinx.serialization.Serializable
data class FriendDto(
    val id: String,
    val email: String,
    val photoUrl: String,
    val displayName: String
)
