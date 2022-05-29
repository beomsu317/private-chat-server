package com.beomsu317.use_case.chat

import com.beomsu317.use_case.chat.dto.RoomDto
import com.beomsu317.use_case.chat.repository.RoomRepository
import com.beomsu317.use_case.exception.RoomNotFoundException
import com.beomsu317.use_case.exception.UnknownUserException
import io.ktor.auth.jwt.*

class GetRoomInfoUseCase(
    private val repository: RoomRepository
) {
    suspend operator fun invoke(roomId: String): GetRoomInfoResult {
        val room = repository.getRoomById(roomId) ?: throw RoomNotFoundException()
        return GetRoomInfoResult(room.toDto())
    }
}

@kotlinx.serialization.Serializable
data class GetRoomInfoResult(
    val room: RoomDto
)