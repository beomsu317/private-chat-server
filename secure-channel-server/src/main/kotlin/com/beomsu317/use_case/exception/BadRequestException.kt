package com.beomsu317.use_case.exception

import io.ktor.http.*

abstract class BadRequestException(code: Int, message: String): BaseException(HttpStatusCode.BadRequest, code, message)