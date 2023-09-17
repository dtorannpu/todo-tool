package com.example.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TodoRoutesTest {
    @Test
    fun test1() = testApplication {
        val token = JWT.create()
            .withClaim("sub", "testid")
            .sign(Algorithm.none())
        client.get("/todos") {
            headers {
                append("Authorization", "Bearer $token")
            }
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }
}