package com.beomsu317.use_case.chat.controller

import com.beomsu317.entity.Room
import io.ktor.http.cio.websocket.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap


class UserSessionController {
    private val connectedSessions = ConcurrentHashMap<String, DefaultWebSocketSession>()

    suspend fun addSession(userId: String, defaultWebSocketSession: DefaultWebSocketSession) {
        connectedSessions.putIfAbsent(userId, defaultWebSocketSession)
    }

    suspend fun sendMessage(id: String, message: String) {
        connectedSessions.get(id)?.send(message)
    }

    suspend fun removeSession(userId: String) {
        connectedSessions.remove(userId)
    }
}