package com.example

import com.example.config.AppConfig
import com.example.config.setupConfig
import com.example.dao.TodoDao
import com.example.dao.TodoDaoImpl
import com.example.database.DatabaseFactory
import com.example.plugins.*
import io.ktor.server.application.*
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


fun Application.testModule(koinModules: List<Module> = listOf(appTestModule)) {
    install(Koin) {
        slf4jLogger()
        modules(koinModules)
    }
    setupConfig()
    val databaseFactory by inject<DatabaseFactory>()
    databaseFactory.connect()

    configureHTTP()
    configureJWTTest()
    configureSerialization()
    configureValidation()
    configStatusPages()
    configureRouting()
}

val appTestModule = module {
    single { AppConfig() }
    factory<DatabaseFactory> { DatabaseFactoryForServerTest(get()) }
    single<TodoDao> { TodoDaoImpl() }
}