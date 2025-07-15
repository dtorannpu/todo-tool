package com.example

import com.example.config.AppConfig
import com.example.database.DatabaseFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

class DatabaseFactoryForServerTest(
    appConfig: AppConfig,
) : DatabaseFactory {
    private val dbConfig = appConfig.databaseConfig

    override fun connect() {
        Database.connect(hikari())
        SchemaDefinition.createSchema()
    }

    override fun close() {
        // not needed
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = dbConfig.driverClassName
        config.jdbcUrl = dbConfig.jdbcUrl
        config.username = dbConfig.username
        config.password = dbConfig.password
        config.maximumPoolSize = dbConfig.maximumPoolSize
        config.isAutoCommit = false
        config.validate()
        return HikariDataSource(config)
    }
}
