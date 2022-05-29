package com.beomsu317.use_case.exception

import io.ktor.http.*

abstract class BadRequestException(code: Int, message: String): BaseException(HttpStatusCode.BadRequest, code, message)

class FriendIdNotSetException(code: Int = 1, message: String = "FriendId parameter is not set"): BadRequestException(code, message)

class RoomIdNotSetException(code: Int = 1, message: String = "RoomId parameter is not set"): BadRequestException(code, message)