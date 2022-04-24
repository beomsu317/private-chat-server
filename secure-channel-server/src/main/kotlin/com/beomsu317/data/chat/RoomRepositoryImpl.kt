package com.beomsu317.data.chat

import com.beomsu317.entity.Room
import com.beomsu317.use_case.chat.repository.RoomRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class RoomRepositoryImpl(
    private val db: CoroutineDatabase,
    private val dispatcher: CoroutineDispatcher
): RoomRepository {

    private val rooms = db.getCollection<Room>()

    override suspend fun insertRoom(room: Room): Boolean {
        return withContext(dispatcher) {
            rooms.insertOne(room).wasAcknowledged()
        }
    }

    override suspend fun getRoomById(id: Id<Room>): Room? {
        return withContext(dispatcher) {
            rooms.findOne(Room::id eq id)
        }
    }

    override suspend fun updateRoom(room: Room) {
        return withContext(dispatcher) {
            rooms.updateOne(Room::id eq room.id, room)
        }
    }

    override suspend fun deleteRoom(room: Room) {
        return withContext(dispatcher) {
            rooms.deleteOne(Room::id eq room.id)
        }
    }
}