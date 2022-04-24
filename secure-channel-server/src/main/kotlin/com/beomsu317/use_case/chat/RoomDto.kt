package com.beomsu317.use_case.chat

import org.litote.kmongo.Id

@kotlinx.serialization.Serializable
data class RoomDto(
    val id: String,
    val title: String,
    val owner: String,
    val users: Set<String>,
)