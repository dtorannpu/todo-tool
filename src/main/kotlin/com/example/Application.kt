package com.example

import com.example.dao.DatabaseFactory
import io.ktor.server.application.*
import com.example.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    DatabaseFactory.init(
        environment.config.property("example.db.driverClassName").getString(),
        environment.config.property("example.db.url").getString(),
        environment.config.property("example.db.user").getString(),
        environment.config.property("example.db.password").getString()
    )
    configureHTTP()
    configureSerialization()
    configureValidation()
    configStatusPages()
    //configureDatabases()
    configureRouting()
}
