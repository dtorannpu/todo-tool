package com.example.plugins

import com.auth0.jwk.JwkProviderBuilder
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import java.util.concurrent.TimeUnit

fun validateCredential(
    credential: JWTCredential,
    audience: String,
): JWTPrincipal? {
    val containsAudience = credential.payload.audience.contains(audience)

    if (containsAudience) {
        return JWTPrincipal(credential.payload)
    }

    return null
}

fun Application.configureJWT() {
    val jwt = environment.config.config("jwt")
    val audience = jwt.property("audience").getString()
    val issuer = jwt.property("issuer").getString()

    val jwkProvider =
        JwkProviderBuilder(issuer)
            .cached(10, 24, TimeUnit.HOURS)
            .rateLimited(10, 1, TimeUnit.MINUTES)
            .build()

    install(Authentication) {
        jwt("auth0") {
            verifier(jwkProvider, issuer)
            validate { credential -> validateCredential(credential, audience) }
        }
    }
}
