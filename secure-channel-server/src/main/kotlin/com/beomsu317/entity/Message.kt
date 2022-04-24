package com.beomsu317.entity

import org.litote.kmongo.Id
import java.time.LocalDateTime

data class Message(
    val roomId: Id<Room>,
    val senderId: Id<User>,
    val time: LocalDateTime,
    val displayName: String,
    val message: String,
)