package com.beomsu317.use_case.exception

import io.ktor.http.*


abstract class BaseException(val statusCode: HttpStatusCode, val code: Int, message: String) : Exception(message)

