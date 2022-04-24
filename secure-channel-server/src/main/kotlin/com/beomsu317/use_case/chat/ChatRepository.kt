package com.beomsu317.use_case.chat

import com.beomsu317.entity.Room
import org.litote.kmongo.Id

interface ChatRepository {

    suspend fun insertRoom(room: Room): Boolean

    suspend fun getRoomById(id: Id<Room>): Room?

    suspend fun updateRoom(room: Room)

    suspend fun deleteRoom(room: Room)
}