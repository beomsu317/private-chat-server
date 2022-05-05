package com.beomsu317.use_case.chat.repository

import com.beomsu317.entity.Room
import org.litote.kmongo.Id

interface RoomRepository {

    suspend fun insertRoom(room: Room): Boolean

    suspend fun getRoomById(id: String): Room?

    suspend fun updateRoom(room: Room)

    suspend fun deleteRoom(room: Room)
}