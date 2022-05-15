package com.beomsu317.use_case.exception

import io.ktor.http.*

abstract class ConflictException(code: Int, message: String) :
    BaseException(HttpStatusCode.Conflict, code, message)

class EmailConflictException(
    code: Int = 1,
    message: String = "Email already in use"
) : ConflictException(code, message)

class FriendConflictException(
    code: Int = 2,
    message: String = "Friend already added"
) : ConflictException(code, message)
