package com.beomsu317.use_case.exception

import io.ktor.http.*

abstract class UnAuthorizedException(code: Int, message: String) :
    BaseException(HttpStatusCode.Unauthorized, code, message)

class LoginFailedException(
    code: Int = 1,
    message: String = "Incorrect email or password"
) : UnAuthorizedException(code, message)

class UnknownUserException(
    code: Int = 2,
    message: String = "Unknown user"
): UnAuthorizedException(code, message)