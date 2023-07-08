package com.example.plugins

import com.example.routes.todoRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*

fun Application.configureRouting() {
    routing {
        todoRouting()
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
        openAPI(path="openapi", swaggerFile = "openapi/documentation.yaml")
    }
}
