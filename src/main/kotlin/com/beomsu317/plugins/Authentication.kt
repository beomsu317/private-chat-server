package com.beomsu317.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.beomsu317.use_case.user.GetUserByIdUseCase
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import org.koin.ktor.ext.inject

fun Application.configureAuthentication() {
    install(Authentication) {
        jwt("jwt") {
            validate { credential ->
                val id = credential.payload.getClaim("id").asString()
                if (id.isNotEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            val secret = environment.config.property("jwt.secret").getString()
            verifier(JWT
                .require(Algorithm.HMAC256(secret))
                .build()
            )
        }
    }
}