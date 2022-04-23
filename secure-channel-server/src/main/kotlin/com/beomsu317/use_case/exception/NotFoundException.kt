package com.beomsu317.use_case.exception

import io.ktor.http.*

abstract class NotFoundException(code: Int, message: String) :
    BaseException(HttpStatusCode.NotFound, code, message)

class UserDoesNotExistsException(code: Int = 1, message: String = "User does not exists"): NotFoundException(code, message)
class ConfigurationNotFoundException(code: Int = 2, message: String = "Configuration not found"): NotFoundException(code, message)