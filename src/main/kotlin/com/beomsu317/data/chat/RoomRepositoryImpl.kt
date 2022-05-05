package com.beomsu317.data.chat

import com.beomsu317.entity.Room
import com.beomsu317.use_case.chat.repository.RoomRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

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

    override suspend fun getRoomById(id: String): Room? {
        return withContext(dispatcher) {
            rooms.findOne(Room::id eq ObjectId(id).toId())
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