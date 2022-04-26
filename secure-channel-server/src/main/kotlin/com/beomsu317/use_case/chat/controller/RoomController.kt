package com.beomsu317.use_case.chat.controller

import com.beomsu317.use_case.exception.ChatRoomNotFoundException
import io.ktor.http.cio.websocket.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.HashMap
import kotlin.collections.HashSet


class RoomController {
    private val rooms =
        ConcurrentHashMap<String, MutableSet<DefaultWebSocketSession>>(HashMap<String, HashSet<DefaultWebSocketSession>>())

    suspend fun addSession(roomId: String, defaultWebSocketSession: DefaultWebSocketSession) {
        rooms.putIfAbsent(roomId, Collections.synchronizedSet(HashSet<DefaultWebSocketSession>()))
        val sessions = rooms.get(roomId) ?: throw ChatRoomNotFoundException()
        sessions += defaultWebSocketSession
        rooms.put(roomId, sessions)
    }

    suspend fun getSessions(roomId: String): Set<DefaultWebSocketSession> {
        return rooms.get(roomId) ?: emptySet()
    }

    suspend fun sendMessage(roomId: String, message: String) {
        getSessions(roomId).forEach {
            it.send(message)
        }
    }

    suspend fun removeSession(roomId: String, defaultWebSocketServerSession: DefaultWebSocketSession) {
        val sessions = rooms.get(roomId) ?: throw ChatRoomNotFoundException()
        sessions -= defaultWebSocketServerSession
        if (sessions.isEmpty()) {
            rooms.remove(roomId)
        }
    }
}