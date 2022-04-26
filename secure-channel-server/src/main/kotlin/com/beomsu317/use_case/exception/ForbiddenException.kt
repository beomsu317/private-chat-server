package com.beomsu317.use_case.exception

import io.ktor.http.*

abstract class ForbiddenException(code: Int, message: String) :
    BaseException(HttpStatusCode.Forbidden, code, message)

class PasswordConstraintsException(code: Int = 1, message: String = "Password must be between 8 and 32 characters") :
    ForbiddenException(code, message)

class NoPermissionForRoomException(code: Int = 2, message: String = "You have no permission this room") :
    ForbiddenException(code, message)