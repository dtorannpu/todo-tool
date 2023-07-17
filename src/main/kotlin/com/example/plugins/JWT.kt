package com.example.plugins

import com.auth0.jwk.JwkProviderBuilder
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.util.concurrent.TimeUnit

fun validateCreds(credential: JWTCredential): JWTPrincipal? {
    val containsAudience = credential.payload.audience.contains(System.getenv("AUDIENCE"))

    if (containsAudience) {
        return JWTPrincipal(credential.payload)
    }

    return null
}

fun Application.configureJWT() {
    val jwkProvider = JwkProviderBuilder(System.getenv("ISSUER"))
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()

    install(Authentication) {
        jwt("auth0") {
            verifier(jwkProvider, System.getenv("ISSUER"))
            validate { credential -> validateCreds(credential) }
        }
    }
}