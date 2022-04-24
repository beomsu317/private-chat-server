package com.beomsu317.use_case.exception

import io.ktor.http.*

abstract class NotFoundException(code: Int, message: String) :
    BaseException(HttpStatusCode.NotFound, code, message)

class UserDoesNotFoundException(code: Int = 1, message: String = "User does not found") :
    NotFoundException(code, message)

class ConfigurationNotFoundException(code: Int = 2, message: String = "Configuration not found") :
    NotFoundException(code, message)

class ParameterNotFoundException(code: Int = 3, message: String = "Required parameter is not present") :
    NotFoundException(code, message)

class ChattingRoomNotFoundException(code: Int = 4, message: String = "Chatting room is not found") :
    NotFoundException(code, message)

class RoomDoesNotFoundException(code: Int = 5, message: String = "Room does not found") :
    NotFoundException(code, message)
