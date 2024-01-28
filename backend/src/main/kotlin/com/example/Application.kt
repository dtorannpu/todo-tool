package com.example

import com.example.config.setupConfig
import com.example.database.DatabaseFactory
import com.example.di.appModule
import com.example.plugins.configStatusPages
import com.example.plugins.configureHTTP
import com.example.plugins.configureJWT
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import com.example.plugins.configureValidation
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.module.Module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module(koinModules: List<Module> = listOf(appModule)) {
    install(Koin) {
        slf4jLogger()
        modules(koinModules)
    }
    setupConfig()
    val databaseFactory by inject<DatabaseFactory>()
    databaseFactory.connect()

    configureHTTP()
    configureJWT()
    configureSerialization()
    configureValidation()
    configStatusPages()
    configureRouting()
}
