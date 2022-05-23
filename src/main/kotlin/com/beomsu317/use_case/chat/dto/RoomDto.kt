package com.beomsu317.use_case.chat.dto

@kotlinx.serialization.Serializable
data class RoomDto(
    val id: String,
    val owner: String,
    val users: Set<String>,
)