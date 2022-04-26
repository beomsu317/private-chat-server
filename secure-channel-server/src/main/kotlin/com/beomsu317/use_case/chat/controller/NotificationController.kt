package com.beomsu317.use_case.chat.controller

import com.beomsu317.entity.Room
import com.beomsu317.use_case.exception.SessionNotFoundException
import io.ktor.http.cio.websocket.*
import java.util.concurrent.ConcurrentHashMap

class NotificationController {
    val userSessions = ConcurrentHashMap<String, DefaultWebSocketSession>()

    suspend fun addUserSession(id: String, session: DefaultWebSocketSession) {
        userSessions.put(id, session)
    }

    suspend fun removeUserSession(id: String) {
        userSessions.remove(id)
    }

    suspend fun pushNotification(room: Room) {
        room.users.forEach { userId ->
            val session = userSessions.get(userId.toString()) ?: throw SessionNotFoundException()
            session.send(room.id.toString())
        }
    }
}