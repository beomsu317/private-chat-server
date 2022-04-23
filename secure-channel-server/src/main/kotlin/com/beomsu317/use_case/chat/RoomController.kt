package com.beomsu317.use_case.chat

import com.beomsu317.use_case.exception.ChattingRoomNotFoundException
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class RoomController {
    private val rooms =
        ConcurrentHashMap<String, MutableSet<DefaultWebSocketSession>>(HashMap<String, HashSet<DefaultWebSocketSession>>())

    fun addSession(roomId: String, defaultWebSocketServerSession: DefaultWebSocketServerSession) {
        rooms.putIfAbsent(roomId, Collections.synchronizedSet(HashSet<DefaultWebSocketSession>()))
        val sessions = rooms.get(roomId) ?: throw ChattingRoomNotFoundException()
        sessions += defaultWebSocketServerSession
        rooms.put(roomId, sessions)
    }

    fun getSessions(roomId: String): Set<DefaultWebSocketSession> {
        return rooms.get(roomId) ?: throw ChattingRoomNotFoundException()
    }

    fun removeSession(roomId: String, defaultWebSocketServerSession: DefaultWebSocketServerSession) {
        val sessions = rooms.get(roomId) ?: throw ChattingRoomNotFoundException()
        sessions -= defaultWebSocketServerSession
        if (sessions.isEmpty()) {
            rooms.remove(roomId)
        }
    }
}