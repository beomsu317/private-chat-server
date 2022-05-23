package com.beomsu317.use_case.chat

import com.beomsu317.entity.Room
import com.beomsu317.use_case.chat.dto.RoomDto

fun Room.toDto() = RoomDto(
    id = id.toString(),
    owner = owner.toString(),
    users = users.map { it.toString() }.toSet()
)