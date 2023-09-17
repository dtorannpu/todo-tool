package com.example

import com.example.plugins.*
import io.ktor.server.application.*


fun Application.testModule() {
    com.example.dao.DatabaseFactory.init(
        environment.config.property("example.db.driverClassName").getString(),
        environment.config.property("example.db.url").getString(),
        environment.config.property("example.db.user").getString(),
        environment.config.property("example.db.password").getString()
    )
    configureHTTP()
    configureJWTTest()
    configureSerialization()
    configureValidation()
    configStatusPages()
    configureRouting()
}
