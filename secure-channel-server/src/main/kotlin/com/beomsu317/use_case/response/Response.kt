package com.beomsu317.use_case.response

@kotlinx.serialization.Serializable
data class Response<T>(
    val code: String? = null,
    val message: String? = null,
    val result: T? = null
)