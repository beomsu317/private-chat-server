package com.beomsu317.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*

fun Application.configureAuthentication() {
    install(Authentication) {
        jwt("jwt") {
            validate { credential ->
                if (credential.payload.getClaim("email").asString() != "") {
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