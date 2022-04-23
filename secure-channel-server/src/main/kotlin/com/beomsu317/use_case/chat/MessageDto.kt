package com.beomsu317.use_case.chat

@kotlinx.serialization.Serializable
data class MessageDto(
    val time: String,
    val displayName: String,
    val message: String
)