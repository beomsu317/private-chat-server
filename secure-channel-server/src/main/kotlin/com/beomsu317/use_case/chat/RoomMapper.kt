package com.beomsu317.use_case.chat

import com.beomsu317.entity.Room

fun Room.toDto() = RoomDto(
    id = id.toString(),
    title = title,
    owner = owner.toString(),
    users = users.map { it.toString() }.toSet()
)