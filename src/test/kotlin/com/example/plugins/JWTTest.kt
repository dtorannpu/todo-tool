package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureJWTTest() {
    install(Authentication) {
        jwt("auth0") {
            verifier { _ -> JWT.require(Algorithm.none()).build() }
            validate { jwtCredential -> JWTPrincipal(jwtCredential.payload) }
        }
    }
}