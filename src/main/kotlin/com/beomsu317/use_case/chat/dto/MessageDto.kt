package com.beomsu317.use_case.chat.dto

@kotlinx.serialization.Serializable
data class MessageDto(
    val senderId: String,
    val roomId: String,
    val time: String,
    val displayName: String,
    val message: String,
)