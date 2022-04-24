package com.beomsu317.use_case.chat.mapper

import com.beomsu317.entity.Message
import com.beomsu317.use_case.chat.dto.MessageDto
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId
import java.time.LocalDateTime

fun Message.toDto() = MessageDto(
    roomId = roomId.toString(),
    senderId = senderId.toString(),
    time = time.toString(),
    displayName = displayName,
    message = message
)

fun MessageDto.toEntity() = Message(
    roomId = ObjectId(roomId).toId(),
    senderId = ObjectId(senderId).toId(),
    time = LocalDateTime.parse(time),
    displayName = displayName,
    message = message
)