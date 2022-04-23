package com.beomsu317.use_case.exception

import io.ktor.http.*

abstract class UnSupportedMediaTypeException(code: Int, message: String) :
    BaseException(statusCode = HttpStatusCode.UnsupportedMediaType, code = code, message = message)

class UnSupportedImageTypeException(code: Int = 1, message: String = "Unsupported image type"): UnSupportedMediaTypeException(code, message)