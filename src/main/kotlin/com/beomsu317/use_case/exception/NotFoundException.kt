package com.beomsu317.use_case.exception

import io.ktor.http.*

abstract class NotFoundException(code: Int, message: String) :
    BaseException(HttpStatusCode.NotFound, code, message)

class UserNotFoundException(code: Int = 1, message: String = "User not found") :
    NotFoundException(code, message)

class ConfigurationNotFoundException(code: Int = 2, message: String = "Configuration not found") :
    NotFoundException(code, message)

class ParameterNotFoundException(code: Int = 3, message: String = "Required parameter is not present") :
    NotFoundException(code, message)

class ChatRoomNotFoundException(code: Int = 4, message: String = "Chat room not found") :
    NotFoundException(code, message)

class RoomNotFoundException(code: Int = 5, message: String = "Room not found") :
    NotFoundException(code, message)

class SessionNotFoundException(code: Int = 6, message: String = "Session not found") :
    NotFoundException(code, message)
