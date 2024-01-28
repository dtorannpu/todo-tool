package com.example.config

import io.ktor.server.application.Application
import org.koin.ktor.ext.inject

class AppConfig {
    lateinit var databaseConfig: DatabaseConfig
}

fun Application.setupConfig() {
    val appConfig by inject<AppConfig>()
    val databaseObject = environment.config.config("ktor.database")
    val driverClassName = databaseObject.property("driverClassName").getString()
    val jdbcUrl = databaseObject.property("jdbcUrl").getString()
    val username = databaseObject.property("username").getString()
    val password = databaseObject.property("password").getString()
    val maximumPoolSize = databaseObject.property("maximumPoolSize").getString().toInt()
    appConfig.databaseConfig = DatabaseConfig(driverClassName, jdbcUrl, username, password, maximumPoolSize)
}
